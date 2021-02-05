package ml.market.cors.domain.security.member.handler;

import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.cookie.CookieManagement;
import ml.market.cors.domain.util.token.JwtTokenManagement;
import ml.market.cors.domain.util.token.TokenAttribute;
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
    private Blacklist_TokenRepository blacklist_tokenRepository;

    @Autowired
    private TokenInfoRepository tokenInfoRepository;

    @Autowired
    private JwtTokenManagement jwtTokenManagement;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        JwtCertificationToken token = (JwtCertificationToken) SecurityContextHolder.getContext().getAuthentication();
        if(token == null){
            return;
        }

        Cookie[] cookies = request.getCookies();
        String[] tokenNames = {TokenAttribute.ACCESS_TOKEN, TokenAttribute.REFRESH_TOKEN};
        for (String tokenName : tokenNames) {
            Cookie cookie = CookieManagement.search(tokenName, cookies);
            if(cookie == null){
                continue;
            }
            if(jwtTokenManagement.delete(cookie.getValue(), tokenName) == false){
                //log
            }
            CookieManagement.delete(response, tokenName, cookies);
        }
        response.reset();
    }
}
