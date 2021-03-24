package ml.market.cors.domain.security.member.provider;

import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.security.member.service.MemberLoginAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class MemberLoginAuthProvider implements AuthenticationProvider {
    @Autowired
    private MemberLoginAuthService memberLoginAuthService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberLoginAuthProvider(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private boolean comparePassword(String reqPassword, String password) {
        boolean bResult = bCryptPasswordEncoder.matches(reqPassword, password);
        return bResult;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();
        String passwd = (String) authentication.getCredentials();
        MemberDAO memberDAO = memberLoginAuthService.loadUserByUsername(email);
        String orgPasswd =  memberDAO.getPassword();
        boolean bResult = comparePassword(passwd, orgPasswd);
        if(!bResult) {
            throw new BadCredentialsException(email);
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(memberDAO, passwd, memberDAO.getAuthorities());
        return token;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        Class<UsernamePasswordAuthenticationToken> authentication = UsernamePasswordAuthenticationToken.class;
        if(aClass.equals(authentication)){
            return true;
        }
        return false;
    }
}
