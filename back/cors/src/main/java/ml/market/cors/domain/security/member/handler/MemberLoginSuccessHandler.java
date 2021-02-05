package ml.market.cors.domain.security.member.handler;

import ml.market.cors.domain.article.entity.dao.Wish_listDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.entity.TokenInfoDAO;
import ml.market.cors.domain.member.map.MemberParam;
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
            List<MemberDAO> memberDAOList = memberRepository.findByMemberId(member_id);
            if(memberDAOList == null){
                throw new RuntimeException();
            }
            MemberDAO memberDAO = memberDAOList.get(0);
            TokenInfoDAO tokenInfoDAO = new TokenInfoDAO(refreshToken, memberDAO.getMember_id(), expireTime);
            tokenInfoDAO = tokenInfoRepository.save(tokenInfoDAO);
            response.setContentType("application/json");
            eCookie cookAttr = eCookie.ACCESS_TOKEN;
            Cookie cookie = CookieManagement.add(cookAttr.getName(), cookAttr.getMaxAge(), cookAttr.getPath(), accessToken);
            response.addCookie(cookie);
            cookAttr = eCookie.REFRESH_TOKEN;
            cookie = CookieManagement.add(cookAttr.getName(), cookAttr.getMaxAge(), cookAttr.getPath(), Long.toString(tokenInfoDAO.getTokenindex()));
            response.addCookie(cookie);
            response.setHeader(MemberParam.NICKNAME, memberDAO.getNickname());
            response.setHeader(MemberParam.PROFILE_IMG, memberDAO.getProfile_img());
            response.setHeader(MemberParam.LATITUDE, String.valueOf(memberDAO.getLatitude()));
            response.setHeader(MemberParam.LONGITUDE, String.valueOf(memberDAO.getLongitude()));
            response.setHeader(MemberParam.ROLE, memberDAO.getRole().getRole());
            List<Wish_listDAO> wish_listDAOList = memberDAO.getWish_listDAO();
            if(wish_listDAOList != null){
                if(wish_listDAOList.size() > 0){
                    response.setHeader(MemberParam.WISHLIST, String.valueOf(wish_listDAOList));
                }
            }

        } catch(Exception e) {
            response.reset();
            response.setStatus(400);
        }
    }

}
