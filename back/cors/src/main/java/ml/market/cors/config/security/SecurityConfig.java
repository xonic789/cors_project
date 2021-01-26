package ml.market.cors.config.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.security.CookieSecurityContextRepository;
import ml.market.cors.domain.security.member.filter.MemberLoginAuthFilter;

import java.lang.reflect.Member;
import java.util.List;

import ml.market.cors.domain.security.member.handler.MemberLoginSuccessHandler;
import ml.market.cors.domain.security.member.manager.MemberProviderManager;
import ml.market.cors.domain.security.member.provider.MemberLoginAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.LinkedList;


@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CookieSecurityContextRepository mCookieSecurityContextRepository;

    @Override
    public void configure(WebSecurity web)  {
       web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.httpBasic().disable()
                    .csrf().disable()
                    .securityContext().securityContextRepository(mCookieSecurityContextRepository)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/login").anonymous()
                    .antMatchers("/join").anonymous()
                    .antMatchers("/test").hasRole("ADMIN")
                    .antMatchers("/**").permitAll()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .formLogin()
                    .disable()
                    .addFilter(getAccountAuthenticationFilter());

    }

    @Bean
    public MemberLoginAuthFilter getAccountAuthenticationFilter() throws Exception {
        List<AuthenticationProvider> providers = new LinkedList<AuthenticationProvider>();
        providers.add(getMemberLoginAuthProvider());
        MemberLoginAuthFilter memberLoginAuthFilter = new MemberLoginAuthFilter(new MemberProviderManager(providers));
        memberLoginAuthFilter.setFilterProcessesUrl("/login");
        memberLoginAuthFilter.setAuthenticationSuccessHandler(getMemberLoginSuccessHandler());
        return memberLoginAuthFilter;
    }

    @Bean
    public MemberLoginAuthProvider getMemberLoginAuthProvider(){
        return new MemberLoginAuthProvider();
    }
    @Bean
    public MemberLoginSuccessHandler getMemberLoginSuccessHandler(){
        return new MemberLoginSuccessHandler();
    }
}
