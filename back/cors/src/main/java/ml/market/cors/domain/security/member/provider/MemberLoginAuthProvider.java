package ml.market.cors.domain.security.member.provider;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.security.member.role.MemberGrantAuthority;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.security.member.service.MemberLoginAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

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
        MemberDAO memberDAO = null;
        memberDAO = memberLoginAuthService.loadUserByUsername(email);
        if(memberDAO == null){
            throw new UsernameNotFoundException(email);
        }

        String orgPasswd =  memberDAO.getPassword();
        boolean bResult = comparePassword(passwd, orgPasswd);
        if(!bResult) {
            throw new BadCredentialsException(email);
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(memberDAO.getMember_id(), passwd, memberDAO.getAuthorities());
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
