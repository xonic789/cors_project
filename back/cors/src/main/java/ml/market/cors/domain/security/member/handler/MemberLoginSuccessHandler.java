package ml.market.cors.domain.security.member.handler;

import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.entity.TokenInfoDAO;
import ml.market.cors.domain.util.cookie.CookieManagement;
import ml.market.cors.domain.util.token.JwtTokenManagement;
import ml.market.cors.domain.util.token.TokenAttribute;
import ml.market.cors.domain.util.cookie.eCookie;
import ml.market.cors.repository.member.MemberRepository;
import ml.market.cors.repository.member.TokenInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class MemberLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private TokenInfoRepository tokenInfoRepository;

    @Autowired
    private JwtTokenManagement mJwtTokenManagement;

    @Autowired
    private MemberRepository memberRepository;

    private Map getClaimsMap(String type, long member_id, List memberRoles){
        String token = null;
        Map claims = new HashMap();
        Map headers = new HashMap();
        Date expireDate = null;
        claims.put(TokenAttribute.MEMBER_ROLE, memberRoles);
        claims.put(TokenAttribute.ID_CLAIM, member_id);
        claims.put(TokenAttribute.IAT_CLAIM, System.currentTimeMillis());
        headers.put(TokenAttribute.ALG_HEADER, TokenAttribute.HS256);
        headers.put(TokenAttribute.TYP_HEADER, TokenAttribute.JWT);
        if(type.equals(TokenAttribute.ACCESS_TOKEN)){
            expireDate = mJwtTokenManagement.createExpireDate(TokenAttribute.ACCESS_EXPIRETIME);
        }else if(type.equals(TokenAttribute.REFRESH_TOKEN)){
            expireDate = mJwtTokenManagement.createExpireDate(TokenAttribute.REFRESH_EXPIRETIME);
        }
        token = mJwtTokenManagement.create(expireDate, headers, claims);
        Map resultMap = new HashMap();
        resultMap.put(TOKEN_CLAIM_NAME, token);
        resultMap.put(DATE_CLAIM_NAME, expireDate);
        return resultMap;
    }

    private final String TOKEN_CLAIM_NAME = "TOKEN_CLAIM";

    private final String DATE_CLAIM_NAME = "DATE_CLAIM";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        long member_id =  (long) authentication.getPrincipal();
        List memberRoles = (List)authentication.getAuthorities();
        Map claims = getClaimsMap(TokenAttribute.ACCESS_TOKEN, member_id, memberRoles);
        String accessToken = (String) claims.get(TOKEN_CLAIM_NAME);
        claims = getClaimsMap(TokenAttribute.REFRESH_TOKEN, member_id, memberRoles);
        String refreshToken = (String) claims.get(TOKEN_CLAIM_NAME);
        Date refreshTokenExpireTime = (Date) claims.get(DATE_CLAIM_NAME);
        if(accessToken == null || refreshToken == null){
            response.setStatus(400);
            return;
        }

        try {
            Long expireTime = refreshTokenExpireTime.getTime();
            Optional<MemberDAO> optional = memberRepository.findById(member_id);
            MemberDAO memberDAO = optional.get();
            TokenInfoDAO tokenInfoDAO = new TokenInfoDAO(refreshToken, memberDAO, expireTime);
            tokenInfoRepository.save(tokenInfoDAO);
            response.setContentType("application/json");
            eCookie cookAttr = eCookie.ACCESS_TOKEN;
            Cookie cookie = CookieManagement.add(cookAttr.getName(), cookAttr.getMaxAge(), cookAttr.getPath(), accessToken);
            response.addCookie(cookie);
            cookAttr = eCookie.REFRESH_TOKEN;
            cookie = CookieManagement.add(cookAttr.getName(), cookAttr.getMaxAge(), cookAttr.getPath(), refreshToken);
            response.addCookie(cookie);
        } catch(Exception e) {
            response.setStatus(400);
            CookieManagement.delete(response, eCookie.ACCESS_TOKEN.getName(), request.getCookies());
            CookieManagement.delete(response, eCookie.REFRESH_TOKEN.getName(), request.getCookies());
        }
    }

}
