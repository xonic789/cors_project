package ml.market.cors.domain.security.common.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.map.MemberParam;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.security.member.role.MemberGrantAuthority;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.util.cookie.CookieManagement;
import ml.market.cors.domain.util.cookie.eCookie;
import ml.market.cors.domain.util.token.LoginTokenManagement;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class CookieSecurityContextRepository implements SecurityContextRepository {
    private final LoginTokenManagement loginTokenManagement;

    private final MemberRepository memberRepository;

    private String notFoundAccessTokenCookie(@NonNull HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Cookie cook = CookieManagement.search(eCookie.REFRESH_TOKEN.getName(), cookies);
        if(cook == null){
            return null;
        }
        String refreshTokenIndexToken = cook.getValue();
        Map<String, Object> claims = loginTokenManagement.getClaims(refreshTokenIndexToken);
        if(claims == null){
            return null;
        }
        long refreshTokenIndex = ((Number)claims.get(LoginTokenManagement.REFRESH_TOKEN_INDEX)).longValue();
        String accessToken = loginTokenManagement.refresh(refreshTokenIndex);
        if(accessToken == null){
            log.debug("재발급 실패");
            return null;
        }
        return accessToken;
    }

    private Map<String, Object> refresh(Cookie[] cookies){
        Cookie cook = CookieManagement.search(eCookie.REFRESH_TOKEN.getName(), cookies);
        if(cook == null){
            return null;
        }
        Map<String, Object> claims = loginTokenManagement.getClaims(cook.getValue());
        if(claims == null){
            return null;
        }
        Long index = ((Number)claims.get(LoginTokenManagement.REFRESH_TOKEN_INDEX)).longValue();
        String accessToken = loginTokenManagement.refresh(index);
        if (accessToken == null) {
            return null;
        }
        claims = loginTokenManagement.getClaims(accessToken);
        return claims;
    }

    private Map<String, Object> existAccessToken(@NonNull HttpServletResponse response, @NonNull HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Cookie cook = CookieManagement.search(eCookie.ACCESS_TOKEN.getName(), cookies);
        if (!loginTokenManagement.isVerify(cook.getValue())) {
            return null;
        }
        Map<String, Object> claims = loginTokenManagement.getClaims(cook.getValue());
        if(claims != null){
            long memberId = ((Number) claims.get(LoginTokenManagement.ID)).longValue();
            List<MemberDAO> memberDAOList = memberRepository.findByMemberId(memberId);
            if(memberDAOList.size() == 0){
                return null;
            }
            MemberDAO memberDAO = memberDAOList.get(0);
            MemberRole memberRole = memberDAO.getRole();
            List tokenRoleList = (List)claims.get(LoginTokenManagement.ROLE);
            try{
                tokenRoleList = TransListMemberAuthority(tokenRoleList);
            }catch (Exception e){
                return null;
            }
            MemberGrantAuthority tokenRole = (MemberGrantAuthority) tokenRoleList.get(0);
            if(memberRole.getRole() != tokenRole.getAuthority()){
                response.setHeader(MemberParam.ROLE, memberRole.getRole());
                claims = refresh(cookies);
            }
        }else{
            claims = refresh(cookies);
        }
        return claims;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder httpRequestResponseHolder) {
        HttpServletRequest request = httpRequestResponseHolder.getRequest();
        HttpServletResponse response = httpRequestResponseHolder.getResponse();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Cookie cook = CookieManagement.search(eCookie.ACCESS_TOKEN.getName(), request.getCookies());
        Map<String, Object> claims = null;
        if (cook == null) {
            String accessToken = notFoundAccessTokenCookie(request);
            if(accessToken == null){
                loginTokenManagement.logout(request, response);
                return securityContext;
            }
            claims = loginTokenManagement.getClaims(accessToken);
            if(claims == null){
                loginTokenManagement.logout(request, response);
                return securityContext;
            }
            eCookie accessTokenCookParam = eCookie.ACCESS_TOKEN;
            cook = CookieManagement.add(accessTokenCookParam.getName(),accessTokenCookParam.getMaxAge(), accessTokenCookParam.getPath(), accessToken);
            response.addCookie(cook);
        } else if (loginTokenManagement.isBlackList(cook.getValue())) {
            loginTokenManagement.logout(request, response);
            return securityContext;
        } else {
            claims = existAccessToken(response, request);
            if(claims == null){
                loginTokenManagement.logout(request, response);
                return securityContext;
            }
        }

        Long member_id = ((Number) claims.get(LoginTokenManagement.ID)).longValue();
        String email =(String) claims.get(LoginTokenManagement.EMAIL);
        List memberRoles = (List) claims.get(LoginTokenManagement.ROLE);
        try{
            memberRoles = TransListMemberAuthority(memberRoles);
        }catch (Exception e){
            loginTokenManagement.logout(request, response);
            return securityContext;
        }
        JwtCertificationToken authToken = new JwtCertificationToken(member_id ,email, memberRoles);
        securityContext.setAuthentication(authToken);
        return securityContext;
    }

    private List TransListMemberAuthority(@NonNull List<MemberGrantAuthority> roles){
        final String AUTHORITY ="authority";
        List<MemberGrantAuthority> memberRoles = new LinkedList();
        StringBuilder auth = new StringBuilder();
        for (Object role : roles) {
            auth.delete(0, auth.length());
            auth.append((String)((LinkedHashMap) role).get(AUTHORITY));
            switch (auth.toString()){
                case "ROLE_ADMIN":
                    memberRoles.add(new MemberGrantAuthority(MemberRole.ADMIN));
                    break;
                case "ROLE_USER":
                    memberRoles.add(new MemberGrantAuthority(MemberRole.USER));
                    break;
                case "ROLE_CEO":
                    memberRoles.add(new MemberGrantAuthority(MemberRole.CEO));
                    break;
                default:
                    throw new RuntimeException();
            }
        }
        return memberRoles;
    }
    
    @Override
    public void saveContext(SecurityContext securityContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

    }

    @Override
    public boolean containsContext(HttpServletRequest httpServletRequest) {
        return false;
    }
}
