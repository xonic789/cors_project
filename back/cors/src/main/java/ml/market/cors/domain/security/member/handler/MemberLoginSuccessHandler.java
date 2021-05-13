package ml.market.cors.domain.security.member.handler;

import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.entity.TokenInfoDAO;
import ml.market.cors.domain.member.map.MemberParam;
import ml.market.cors.domain.member.query.MemberQuerys;
import ml.market.cors.domain.util.cookie.CookieManagement;
import ml.market.cors.domain.util.token.LoginTokenManagement;
import ml.market.cors.domain.util.cookie.eCookie;
import ml.market.cors.repository.member.TokenInfoRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MemberLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private TokenInfoRepository tokenInfoRepository;

    @Autowired
    private LoginTokenManagement loginTokenManagement;

    @Autowired
    private MemberQuerys memberQuerys;

    private Map<String, Object> setClaims(long member_id, List memberRoles, String email){
        Map<String, Object> claims = new HashMap();
        claims.put(LoginTokenManagement.ROLE, memberRoles);
        claims.put(LoginTokenManagement.ID, member_id);
        claims.put(LoginTokenManagement.EMAIL, email);
        claims.put(LoginTokenManagement.IAT, System.currentTimeMillis());
        return claims;
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        MemberDAO memberDAO = (MemberDAO) authentication.getPrincipal();
        long memberId = memberDAO.getMember_id();
        String email = memberDAO.getEmail();
        List memberRoles = (List)authentication.getAuthorities();
        Map<String, Object> claims = setClaims(memberId, memberRoles, email);
        Map<String, Object> tokenPair = loginTokenManagement.createTokenPair(claims);
        if(tokenPair == null){
            response.setStatus(400);
            return;
        }


        try {
            response.setContentType("application/json");
            eCookie cookAttr = eCookie.ACCESS_TOKEN;
            String accessToken = (String)tokenPair.get(LoginTokenManagement.ACCESS_TOKEN);
            Cookie cookie = CookieManagement.add(cookAttr.getName(), cookAttr.getMaxAge(), cookAttr.getPath(), accessToken);
            response.addCookie(cookie);
            cookAttr = eCookie.REFRESH_TOKEN;
            TokenInfoDAO tokenInfoDAO = tokenInfoRepository.save(new TokenInfoDAO((String)tokenPair.get(LoginTokenManagement.REFRESH_TOKEN), memberId, (long)tokenPair.get(LoginTokenManagement.REFRESH_TOKEN_EXPIRETIME)));
            Long index = tokenInfoDAO.getTokenindex();
            String refreshTokenIndexToken = loginTokenManagement.create(index);
            if(refreshTokenIndexToken == null){
                throw new RuntimeException();
            }
            cookie = CookieManagement.add(cookAttr.getName(), cookAttr.getMaxAge(), cookAttr.getPath(), refreshTokenIndexToken);
            response.addCookie(cookie);
            setHeader(response, memberDAO);
        } catch(Exception e) {
            response.reset();
            throw new RuntimeException();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void setHeader(HttpServletResponse response, MemberDAO memberDAO) throws UnsupportedEncodingException {
        String nickname = Base64.encodeBase64String(memberDAO.getNickname().getBytes(StandardCharsets.UTF_8));
        response.setHeader(MemberParam.NICKNAME, nickname);
        response.setHeader(MemberParam.PROFILE_IMG, memberDAO.getProfile_img());
        response.setHeader(MemberParam.LATITUDE, String.valueOf(memberDAO.getLatitude()));
        response.setHeader(MemberParam.LONGITUDE, String.valueOf(memberDAO.getLongitude()));
        response.setHeader(MemberParam.ROLE, memberDAO.getRole().getRole());
        response.setHeader(MemberParam.SOCIALTYPE, memberDAO.getESocialType().toString());
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

}
