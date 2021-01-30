package ml.market.cors.domain.security.oauth.handler;

import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.entity.TokenInfoDAO;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.security.oauth.enu.SocialType;
import ml.market.cors.domain.util.cookie.CookieManagement;
import ml.market.cors.domain.util.cookie.eCookie;
import ml.market.cors.domain.util.kakao.KaKaoRestManagement;
import ml.market.cors.domain.util.token.JwtTokenManagement;
import ml.market.cors.domain.util.token.TokenAttribute;
import ml.market.cors.repository.member.MemberRepository;
import ml.market.cors.repository.member.TokenInfoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
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
        Map userAttributes = user.getAttributes();
        String email = (String) userAttributes.get("email");
        if(socialType.equals("kakao")){
            Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) authentication.getAuthorities();
            if(!authorities.contains(new SimpleGrantedAuthority("SCOPE_account_email"))){
                return null;
            }
            if(!userAttributes.containsKey("kakao_account")) {
                return null;
            }
            Map kakaoAccountMap = (Map) userAttributes.get("kakao_account");
            email = (String) kakaoAccountMap.get("email");
            if(email == null){
                return null;
            }
        }
        return email;
    }

    private Map createToken(@NonNull MemberDAO memberDAO){
        Map tokenPair = new HashMap();
        Date expireDate = jwtTokenManagement.createExpireDate(TokenAttribute.ACCESS_EXPIRETIME);
        Map header = jwtTokenManagement.getHeader();
        List grantAuthorityRoles = new LinkedList<>();
        long id = memberDAO.getMember_id();
        MemberRole memberRole = memberDAO.getRole();
        grantAuthorityRoles.add(memberRole);


        Map claims = jwtTokenManagement.getClaim(id, grantAuthorityRoles);
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
        String refreshToken = (String) tokenPair.get(TokenAttribute.REFRESH_TOKEN);
        tokenInfoRepository.save(new TokenInfoDAO(refreshToken, id, expireDate.getTime()));
        tokenPair.put(TokenAttribute.REFRESH_TOKEN, token);
        return tokenPair;
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = getEmail(authentication);
        if (email == null) {
            throw new IOException();
        }
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String socialTypeName = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        boolean bResult = memberRepository.existsByEmail(email);
        if (!bResult) {
            SocialType socialType = null;
            if (SocialType.GOOGLE.getValue().equals(socialTypeName)) {
                socialType = SocialType.GOOGLE;
            }
            if (SocialType.NAVER.getValue().equals(socialTypeName)) {
                socialType = SocialType.NAVER;
            }
            if (SocialType.KAKAO.getValue().equals(socialTypeName)) {
                socialType = SocialType.KAKAO;
            }
            memberRepository.save(new MemberDAO(email, MemberRole.USER, "blank", "blank", 0.0, 0.0, email, socialType));
        }

        MemberDAO memberDAO = memberRepository.findByEmail(email);
        Map tokenPair = createToken(memberDAO);
        if (tokenPair == null) {
            throw new RuntimeException();
        }
        StringBuilder tokenBuilder = new StringBuilder((String) tokenPair.get(TokenAttribute.ACCESS_TOKEN));
        eCookie cookieAttributes = eCookie.ACCESS_TOKEN;
        CookieManagement.add(cookieAttributes.getName(), cookieAttributes.getMaxAge(), cookieAttributes.getPath(), tokenBuilder.toString());
        cookieAttributes = eCookie.REFRESH_TOKEN;
        tokenBuilder.delete(0, tokenBuilder.length());
        tokenBuilder.append((String) tokenPair.get(TokenAttribute.ACCESS_TOKEN));
        CookieManagement.add(cookieAttributes.getName(), cookieAttributes.getMaxAge(), cookieAttributes.getPath(), tokenBuilder.toString());
    }
}
