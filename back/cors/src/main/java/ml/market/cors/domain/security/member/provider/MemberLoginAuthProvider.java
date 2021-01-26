package ml.market.cors.domain.security.member.provider;

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
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MemberLoginAuthProvider implements AuthenticationProvider {
    @Autowired
    private MemberLoginAuthService memberLoginAuthService;


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
        if(orgPasswd.equals(passwd)){
            throw new BadCredentialsException(passwd);
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
