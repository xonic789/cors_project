package ml.market.cors.domain.security.member.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import ml.market.cors.domain.member.entity.MemberVO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Getter
class LoginMemberVO{
    private String email;
    private String passwd;

    public LoginMemberVO(@JsonProperty("email") String email, @JsonProperty("passwd") String passwd) {
        this.email = email;
        this.passwd = passwd;
    }
}

public class MemberLoginAuthFilter extends UsernamePasswordAuthenticationFilter {
    public MemberLoginAuthFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        if(SecurityContextHolder.getContext().getAuthentication() != null){
            throw new RuntimeException();
        }

        UsernamePasswordAuthenticationToken token = null;

        try{
            LoginMemberVO loginMemberVO = new ObjectMapper().readValue(request.getInputStream(), LoginMemberVO.class);
            token = new UsernamePasswordAuthenticationToken(loginMemberVO.getEmail(), loginMemberVO.getPasswd());
        } catch (Exception except) {
            throw new RuntimeException();
        }

        setDetails(request, token);
        return getAuthenticationManager().authenticate(token);
    }
}