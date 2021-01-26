package ml.market.cors.domain.security;

import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.util.CookieManagement;
import ml.market.cors.domain.util.JwtTokenManagement;
import ml.market.cors.domain.util.TokenAttribute;
import ml.market.cors.repository.member.Blacklist_TokenRepository;
import ml.market.cors.repository.member.MemberRepository;
import ml.market.cors.repository.member.TokenInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@Component
public class CookieSecurityContextRepository implements SecurityContextRepository {
    @Autowired
    private JwtTokenManagement mJwtTokenManagement;

    @Autowired
    private TokenInfoRepository mTokenInfoRepo;

    @Autowired
    private Blacklist_TokenRepository mBlacklistTokenInfoRepo;

    @Autowired
    private MemberRepository memberRepository;

    private boolean isBlacklistToken(String token, Cookie[] cookies, HttpServletResponse res) {
        if (mJwtTokenManagement.isBlackList(token.toString()) == false) {
            return false;
        }
        Cookie cook = CookieManagement.search(TokenAttribute.ACCESS_TOKEN, cookies);
        String accessToken = null;
        if (cook != null) {
            accessToken = cook.getValue();
        }
        cook = CookieManagement.search(TokenAttribute.REFRESH_TOKEN, cookies);
        String refreshToken = null;
        if (cook != null) {
            refreshToken = cook.getValue();
        }
        mJwtTokenManagement.deleteAllTokenDB(accessToken, refreshToken);
        CookieManagement.delete(res, TokenAttribute.ACCESS_TOKEN, cookies);
        CookieManagement.delete(res, TokenAttribute.REFRESH_TOKEN, cookies);
        return true;
    }

    private boolean isVerify(String token, Cookie[] cookies, HttpServletResponse res) {
        if (mJwtTokenManagement.isVerify(token)) {
            return true;
        }
        return false;
    }

    private Map refresh(Cookie[] cookies, HttpServletResponse res) {
        String token = mJwtTokenManagement.refresh(cookies, res);
        if (token != null) {
            return mJwtTokenManagement.getClaims(token);
        }

        Cookie cook = CookieManagement.search(TokenAttribute.ACCESS_TOKEN, cookies);
        String accessToken = null;
        if (cook != null) {
            accessToken = cook.getValue();
        }
        cook = CookieManagement.search(TokenAttribute.REFRESH_TOKEN, cookies);
        String refreshToken = null;
        if (cook != null) {
            refreshToken = cook.getValue();
        }
        mJwtTokenManagement.deleteAllTokenDB(accessToken, refreshToken);
        CookieManagement.delete(res, TokenAttribute.ACCESS_TOKEN, cookies);
        CookieManagement.delete(res, TokenAttribute.REFRESH_TOKEN, cookies);
        return null;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder httpRequestResponseHolder) {
        HttpServletRequest req = httpRequestResponseHolder.getRequest();
        HttpServletResponse res = httpRequestResponseHolder.getResponse();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Cookie[] cookies = req.getCookies();

        Cookie cook = CookieManagement.search(TokenAttribute.ACCESS_TOKEN, cookies);
        Map claims = null;
        if (cook == null) {
            if (mJwtTokenManagement.isAvailRefresh(cookies) == false) {
                CookieManagement.delete(res, TokenAttribute.ACCESS_TOKEN, cookies);
                CookieManagement.delete(res, TokenAttribute.REFRESH_TOKEN, cookies);
                return securityContext;
            }
            String token = mJwtTokenManagement.refresh(cookies, res);
            if (token == null) {
                return securityContext;
            }
            claims = mJwtTokenManagement.getClaims(token);
        } else if (isBlacklistToken(cook.getValue(), cookies, res)) {
            return securityContext;
        } else {
            if (isVerify(cook.getValue(), cookies, res)) {
                claims = mJwtTokenManagement.getClaims(cook.getValue());
                if (claims == null) {
                    claims = refresh(cookies, res);
                    if (claims == null) {
                        return securityContext;
                    }
                }
            } else {
                String accessToken = cook.getValue();
                cook = CookieManagement.search(TokenAttribute.REFRESH_TOKEN, cookies);
                String refreshToken = cook.getValue();
                mJwtTokenManagement.deleteAllTokenDB(accessToken, refreshToken);
                CookieManagement.delete(res, TokenAttribute.ACCESS_TOKEN, cookies);
                CookieManagement.delete(res, TokenAttribute.REFRESH_TOKEN, cookies);
                return securityContext;
            }
        }

        long member_id = (long) claims.get(TokenAttribute.ID_CLAIM);
        Optional<MemberDAO> optional = memberRepository.findById(member_id);
        MemberDAO memberDAO = optional.get();
        MemberRole memberRole = (MemberRole) claims.get(TokenAttribute.MEMBER_ROLE);
        JwtCertificationToken authToken = new JwtCertificationToken(memberDAO.getEmail(), memberRole);
        securityContext.setAuthentication(authToken);
        return securityContext;
    }

    @Override
    public void saveContext(SecurityContext securityContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

    }

    @Override
    public boolean containsContext(HttpServletRequest httpServletRequest) {
        return false;
    }
}
