package ml.market.cors.domain.security.member.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import ml.market.cors.domain.member.service.MemberVO;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Member;
import java.util.Map;

@Getter
class test{
    private Object data;

}

@Data
class LoginForm{
    private String email;
    private String passwd;

    public LoginForm(String email, String passwd) {
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
            //test loginMemberVO = new ObjectMapper().readValue(request.getInputStream(), test.class);
            //String d = new JSONObject((Map) loginMemberVO.getData()).toString();
            //MemberVO aa= new ObjectMapper().readValue(d, MemberVO.class);
            LoginForm wee = new LoginForm(request.getParameter("email"), request.getParameter("passwd"));
            token = new UsernamePasswordAuthenticationToken(wee.getEmail(), wee.getPasswd());
        } catch (Exception except) {
            throw new RuntimeException();
        }

        setDetails(request, token);
        return getAuthenticationManager().authenticate(token);
    }
}