package ml.market.cors.domain.security.member.handler;

import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.cookie.CookieManagement;
import ml.market.cors.domain.util.token.JwtTokenManagement;
import ml.market.cors.domain.util.token.LoginTokenManagement;
import ml.market.cors.repository.member.Blacklist_TokenRepository;
import ml.market.cors.repository.member.TokenInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberLogoutHandler implements LogoutHandler {
    @Autowired
    private LoginTokenManagement loginTokenManagement;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        JwtCertificationToken token = (JwtCertificationToken) SecurityContextHolder.getContext().getAuthentication();
        if(token == null){
            return;
        }
        loginTokenManagement.logout(request,response);
    }
}
