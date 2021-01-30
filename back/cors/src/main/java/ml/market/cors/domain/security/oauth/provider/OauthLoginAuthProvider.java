package ml.market.cors.domain.security.oauth.provider;

import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.security.member.service.MemberLoginAuthService;
import ml.market.cors.domain.security.oauth.OauthCertificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class OauthLoginAuthProvider implements AuthenticationProvider {
    @Autowired
    private MemberLoginAuthService memberLoginAuthService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OauthCertificationToken oauthCertificationToken = (OauthCertificationToken) authentication;
        MemberDAO memberDAO = null;
        String email = oauthCertificationToken.getName();
        memberDAO = memberLoginAuthService.loadUserByUsername(email);
        if(memberDAO != null){
            throw new RuntimeException();
        }



        return oauthCertificationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        Class<OauthCertificationToken> authentication = OauthCertificationToken.class;
        if(aClass.equals(authentication)){
            return true;
        }

        return false;
    }
}
