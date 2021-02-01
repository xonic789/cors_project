package ml.market.cors.domain.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.entity.TokenInfoDAO;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.security.member.role.MemberGrantAuthority;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.util.cookie.CookieManagement;
import ml.market.cors.domain.util.token.JwtTokenManagement;
import ml.market.cors.domain.util.token.TokenAttribute;
import ml.market.cors.repository.member.Blacklist_TokenRepository;
import ml.market.cors.repository.member.MemberRepository;
import ml.market.cors.repository.member.TokenInfoRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CookieSecurityContextRepository implements SecurityContextRepository {
    private final JwtTokenManagement mJwtTokenManagement;

    private final TokenInfoRepository mTokenInfoRepo;

    private final Blacklist_TokenRepository mBlacklistTokenInfoRepo;

    private final MemberRepository memberRepository;

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
        long token_index = 0;
        if (cook != null) {
            token_index= Long.parseLong(cook.getValue());
            TokenInfoDAO tokenInfoDAO = mJwtTokenManagement.findTokenIndex(token_index);
            if(tokenInfoDAO != null){
                refreshToken = tokenInfoDAO.getHash();
            }
        }

        mJwtTokenManagement.deleteAllTokenDB(accessToken, token_index ,refreshToken);
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
                logout(req, res);
                return securityContext;
            }
        }

        Long member_id = ((Number) claims.get(TokenAttribute.ID_CLAIM)).longValue();
        Optional<MemberDAO> optional = memberRepository.findById(member_id);
        MemberDAO memberDAO = optional.get();
        List memberRoles = (List) claims.get(TokenAttribute.MEMBER_ROLE);
        try{
            memberRoles = TransListMemberAuthority(memberRoles);
        }catch (Exception e){
            logout(req, res);
            return securityContext;
        }
        JwtCertificationToken authToken = new JwtCertificationToken(memberDAO.getEmail(), memberRoles);
        securityContext.setAuthentication(authToken);
        return securityContext;
    }

    /*
    토큰에 넣은 List<MemberGrantAUthority>가 json으로 바뀌면서 List 안에
    객체배열들이 객체로 저장되는게 아니라
    멤버필드에 저장되어서 임시로 변환 메소드를 작성한다.
    시간되면 고치겠습니다.
     */
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

    private void logout(HttpServletRequest req, HttpServletResponse res){
        Cookie[] cookies = req.getCookies();
        Cookie cook = CookieManagement.search(TokenAttribute.ACCESS_TOKEN, cookies);
        String accessToken = null;
        if(cook != null){
            accessToken = cook.getValue();
        }
        cook = CookieManagement.search(TokenAttribute.REFRESH_TOKEN, cookies);
        String refreshToken = null;
        long token_index = 0;
        TokenInfoDAO tokenInfoDAO;
        if(cook != null) {
            token_index = Long.parseLong(cook.getValue());
            tokenInfoDAO = mTokenInfoRepo.findByTokenindex(token_index);
            if(tokenInfoDAO == null){
                return;
            }
        }

        mJwtTokenManagement.deleteAllTokenDB(accessToken, token_index , refreshToken);
        CookieManagement.delete(res, TokenAttribute.ACCESS_TOKEN, cookies);
        CookieManagement.delete(res, TokenAttribute.REFRESH_TOKEN, cookies);
    }

    @Override
    public void saveContext(SecurityContext securityContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

    }

    @Override
    public boolean containsContext(HttpServletRequest httpServletRequest) {
        return false;
    }
}
