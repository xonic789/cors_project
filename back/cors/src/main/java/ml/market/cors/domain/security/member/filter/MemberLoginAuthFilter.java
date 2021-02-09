package ml.market.cors.domain.security.member.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import ml.market.cors.domain.member.map.MemberParam;
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

@Data
class LoginForm{
    private String email;
    private String passwd;

    public LoginForm(@NonNull String email,@NonNull String passwd) {
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
            LoginForm loginForm = new LoginForm(request.getParameter(MemberParam.EMAIL), request.getParameter(MemberParam.PASSWD));
            token = new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPasswd());
        } catch (Exception except) {
            throw new RuntimeException();
        }

        setDetails(request, token);
        return getAuthenticationManager().authenticate(token);
    }
}