package ml.market.cors.domain.security.oauth.handler;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.entity.TokenInfoDAO;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.security.oauth.enu.SocialType;
import ml.market.cors.domain.util.cookie.CookieManagement;
import ml.market.cors.domain.util.cookie.eCookie;
import ml.market.cors.domain.util.token.JwtTokenManagement;
import ml.market.cors.domain.util.token.TokenAttribute;
import ml.market.cors.repository.member.MemberRepository;
import ml.market.cors.repository.member.TokenInfoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OauthSuccessHandler implements AuthenticationSuccessHandler {
    private final MemberRepository memberRepository;

    private final JwtTokenManagement jwtTokenManagement;

    private final TokenInfoRepository tokenInfoRepository;

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

    private Map<String, String> createToken(@NonNull MemberDAO memberDAO){
        Map<String, String> tokenPair = new HashMap<>();
        Date expireDate = jwtTokenManagement.createExpireDate(TokenAttribute.ACCESS_EXPIRETIME);
        Map<String, Object> header = jwtTokenManagement.getHeader();
        List<MemberRole> grantAuthorityRoles = new LinkedList<>();
        long id = memberDAO.getMember_id();
        MemberRole memberRole = memberDAO.getRole();
        grantAuthorityRoles.add(memberRole);


        Map<String, Object> claims = jwtTokenManagement.getClaim(id, grantAuthorityRoles);
        String token = jwtTokenManagement.create(expireDate, header, claims);
        if(token == null){
            return null;
        }
        tokenPair.put(TokenAttribute.ACCESS_TOKEN, token);

        expireDate = jwtTokenManagement.createExpireDate(TokenAttribute.REFRESH_EXPIRETIME);
        claims = jwtTokenManagement.getClaim(id, grantAuthorityRoles);
        token = jwtTokenManagement.create(expireDate, header, claims);
        if(token == null){
            return null;
        }
        String refreshToken = tokenPair.get(TokenAttribute.REFRESH_TOKEN);
        tokenInfoRepository.save(new TokenInfoDAO(refreshToken, id, expireDate.getTime()));
        tokenPair.put(TokenAttribute.REFRESH_TOKEN, token);
        return tokenPair;
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        String email = getEmail(authentication);
        if (email == null) {
            throw new IOException();
        }

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String socialTypeName = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        boolean bResult = memberRepository.existsByEmail(email);
        if (!bResult) {
            for(SocialType type : SocialType.values()){
                if (type.getValue().equals(socialTypeName)) {
                    memberRepository.save(new MemberDAO(email, MemberRole.USER, "blank", "blank", 0.0, 0.0, email, type));
                    break;
                }
            }
        }

        MemberDAO memberDAO = memberRepository.findByEmail(email);
        Map<String, String> tokenPair = createToken(memberDAO);
        if (tokenPair == null) {
            throw new RuntimeException();
        }
        deleteTokenPair(request, response);
        StringBuilder tokenBuilder = new StringBuilder(tokenPair.get(TokenAttribute.ACCESS_TOKEN));
        eCookie cookieAttributes = eCookie.ACCESS_TOKEN;
        CookieManagement.add(cookieAttributes.getName(), cookieAttributes.getMaxAge(), cookieAttributes.getPath(), tokenBuilder.toString());
        cookieAttributes = eCookie.REFRESH_TOKEN;
        tokenBuilder.delete(0, tokenBuilder.length());
        tokenBuilder.append(tokenPair.get(TokenAttribute.ACCESS_TOKEN));
        CookieManagement.add(cookieAttributes.getName(), cookieAttributes.getMaxAge(), cookieAttributes.getPath(), tokenBuilder.toString());
    }

    private void deleteTokenPair(HttpServletRequest request, HttpServletResponse response) {
        String refreshTokenSearchCookNm = eCookie.REFRESH_TOKEN.getName();
        String accessTokenSearchCookNm = eCookie.ACCESS_TOKEN.getName();
        Cookie tokenCookie = CookieManagement.search(accessTokenSearchCookNm, request.getCookies());
        String accessToken = null;
        if(tokenCookie != null){
            accessToken = tokenCookie.getValue();
        }

        tokenCookie = CookieManagement.search(refreshTokenSearchCookNm, request.getCookies());
        String refreshToken = null;
        long refreshTokenIndex = 0;
        if(tokenCookie != null){
            refreshToken = tokenCookie.getValue();
            refreshTokenIndex = Long.parseLong(refreshToken);
            TokenInfoDAO tokenInfoDAO = tokenInfoRepository.findByTokenindex(refreshTokenIndex);
            if(tokenInfoDAO != null){
                refreshToken = tokenInfoDAO.getHash();
            }else{
                refreshToken = null;
            }
        }

        jwtTokenManagement.deleteAllTokenDB(accessToken, refreshTokenIndex, refreshToken);
        CookieManagement.delete(response, accessTokenSearchCookNm, request.getCookies());
        CookieManagement.delete(response, refreshTokenSearchCookNm, request.getCookies());
    }
}
