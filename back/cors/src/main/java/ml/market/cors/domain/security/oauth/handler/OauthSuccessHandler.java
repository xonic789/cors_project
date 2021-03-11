package ml.market.cors.domain.security.oauth.handler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.entity.TokenInfoDAO;
import ml.market.cors.domain.member.map.MemberParam;
import ml.market.cors.domain.member.query.MemberQuerys;
import ml.market.cors.domain.security.member.role.MemberGrantAuthority;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.security.oauth.enums.eSocialType;
import ml.market.cors.domain.util.cookie.CookieManagement;
import ml.market.cors.domain.util.cookie.eCookie;
import ml.market.cors.domain.util.token.LoginTokenManagement;
import ml.market.cors.repository.member.MemberRepository;
import ml.market.cors.repository.member.TokenInfoRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class OauthSuccessHandler implements AuthenticationSuccessHandler {
    private final MemberRepository memberRepository;

    private final LoginTokenManagement loginTokenManagement;

    private final TokenInfoRepository tokenInfoRepository;

    private final MemberQuerys memberQuerys;

    private void setHeader(HttpServletResponse response, MemberDAO memberDAO) throws UnsupportedEncodingException {
        String nickname = Base64.encodeBase64String(memberDAO.getNickname().getBytes(StandardCharsets.UTF_8));
        response.setHeader(MemberParam.NICKNAME, nickname);
        response.setHeader(MemberParam.PROFILE_IMG, memberDAO.getProfile_img());
        response.setHeader(MemberParam.LATITUDE, String.valueOf(memberDAO.getLatitude()));
        response.setHeader(MemberParam.LONGITUDE, String.valueOf(memberDAO.getLongitude()));
        response.setHeader(MemberParam.ROLE, memberDAO.getRole().getRole());
        List<Object> wishIdList = memberQuerys.searchMemberWishArticleList(memberDAO.getMember_id());
        if (wishIdList.size() > 0) {
            response.setHeader(MemberParam.WISHLIST, String.valueOf(wishIdList));
        }

        List<Object> marketList = memberQuerys.searchMemberMarketList(memberDAO.getMember_id());
        if (marketList.size() > 0) {
            response.setHeader(MemberParam.MARKETLIST, String.valueOf(marketList));
        }

        List<Object> articleIdList = memberQuerys.searchMemberArticleIdList(memberDAO.getMember_id());
        if(articleIdList.size() > 0){
            response.setHeader(MemberParam.ARTICLELIST, String.valueOf(articleIdList));
        }

    }

    private String getEmail(Authentication authentication){
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String socialType = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        DefaultOAuth2User user =  (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> userAttributes = user.getAttributes();
        String email = (String) userAttributes.get("email");
        if(socialType.equals("kakao")){
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if(!authorities.contains(new SimpleGrantedAuthority("SCOPE_account_email"))){
                return null;
            }
            if(!userAttributes.containsKey("kakao_account")) {
                return null;
            }

            Map<String, Object> kakaoAccountMap = (Map<String, Object>) userAttributes.get("kakao_account");
            if(!kakaoAccountMap.containsKey("email")){
                return null;
            }
            email = (String) kakaoAccountMap.get("email");
        }
        return email;
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        Cookie accessCookie = CookieManagement.search(eCookie.ACCESS_TOKEN.getName(), request.getCookies());
        Cookie refreshCookie = CookieManagement.search(eCookie.ACCESS_TOKEN.getName(), request.getCookies());
        if(accessCookie != null || refreshCookie != null){
            response.sendRedirect("/");
            return;
        }
        String email = getEmail(authentication);
        if (email == null) {
            response.sendRedirect("/");
            return;
        }

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String socialTypeName = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        String imageUrl = uploadSocialProfileImage(socialTypeName, oAuth2AuthenticationToken.getPrincipal());

        MemberDAO memberDAO;
        boolean bResult = memberRepository.existsByEmail(email);
        if (!bResult) {
            for(eSocialType type : eSocialType.values()){
                if (type.getValue().equals(socialTypeName)) {
                    memberRepository.save(new MemberDAO("blank", imageUrl, email, MemberRole.USER, null, "blank", "blank", 0.0, 0.0, email, type));
                    break;
                }
            }
        } else{
            memberDAO = memberRepository.findByEmail(email);
            if(memberDAO.getESocialType() == eSocialType.NORMAL){
                response.sendRedirect("/");
                return;
            }
        }

        memberDAO = memberRepository.findByEmail(email);
        Map<String, Object> claims = new HashMap<>();
        claims.put(LoginTokenManagement.ID, memberDAO.getMember_id());
        claims.put(LoginTokenManagement.EMAIL, memberDAO.getEmail());
        claims.put(LoginTokenManagement.IAT, System.currentTimeMillis());
        List<GrantedAuthority> role = new LinkedList();
        role.add(new MemberGrantAuthority(memberDAO.getRole()));
        claims.put(LoginTokenManagement.ROLE, role);
        Map<String, Object> tokenPair = loginTokenManagement.createTokenPair(claims);
        if(tokenPair == null){
            throw new RuntimeException();
        }
        try {
            eCookie cookAttr = eCookie.ACCESS_TOKEN;
            String accessToken = (String)tokenPair.get(LoginTokenManagement.ACCESS_TOKEN);
            Cookie cookie = CookieManagement.add(cookAttr.getName(), cookAttr.getMaxAge(), cookAttr.getPath(), accessToken);
            response.addCookie(cookie);
            cookAttr = eCookie.REFRESH_TOKEN;
            TokenInfoDAO tokenInfoDAO = tokenInfoRepository.save(new TokenInfoDAO((String)tokenPair.get(LoginTokenManagement.REFRESH_TOKEN), memberDAO.getMember_id(), (long)tokenPair.get(LoginTokenManagement.REFRESH_TOKEN_EXPIRETIME)));
            long index = tokenInfoDAO.getTokenindex();
            String refreshTokenIndexToken = loginTokenManagement.create(index);
            cookie = CookieManagement.add(cookAttr.getName(), cookAttr.getMaxAge(), cookAttr.getPath(), refreshTokenIndexToken);
            response.addCookie(cookie);
            response.sendRedirect("/home");
            setHeader(response, memberDAO);

        } catch(Exception e) {
            response.reset();
            throw new RuntimeException();
        }
    }

    private String uploadSocialProfileImage(String socialTypeName, OAuth2User principle) {
        Map resultMap;
        String imageUrl = null;
        switch (socialTypeName){
            case "kakao":
                resultMap = principle.getAttributes();
                resultMap = (Map)resultMap.get("properties");
                imageUrl = (String)resultMap.get("profile_image");
                break;
            case "google":
                resultMap = principle.getAttributes();
                imageUrl = (String)resultMap.get("picture");
                break;
            case "naver":
                break;
        }
        return imageUrl;
    }
}
