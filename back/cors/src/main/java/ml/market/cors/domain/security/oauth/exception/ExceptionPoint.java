package ml.market.cors.domain.security.oauth.exception;

import edu.umd.cs.findbugs.annotations.CleanupObligation;
import org.apache.http.protocol.HTTP;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("403에러 근원지 exceptionpoint");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
