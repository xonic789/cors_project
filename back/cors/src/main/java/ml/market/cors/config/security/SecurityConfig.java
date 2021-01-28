package ml.market.cors.config.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.security.CookieSecurityContextRepository;
import ml.market.cors.domain.security.ParentProviderManager;
import ml.market.cors.domain.security.member.filter.MemberLoginAuthFilter;

import java.lang.reflect.Member;
import java.util.List;

import ml.market.cors.domain.security.member.filter.MemberLogoutFilter;
import ml.market.cors.domain.security.member.handler.MemberLoginSuccessHandler;
import ml.market.cors.domain.security.member.handler.MemberLogoutHandler;
import ml.market.cors.domain.security.member.manager.MemberProviderManager;
import ml.market.cors.domain.security.member.provider.MemberLoginAuthProvider;
import ml.market.cors.domain.security.member.service.MemberLoginAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.LinkedList;


@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CookieSecurityContextRepository mCookieSecurityContextRepository;

    @Override
    public void configure(WebSecurity web)  {
       web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder builder, ParentProviderManager parentProviderManager) {
        builder.parentAuthenticationManager(parentProviderManager);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.httpBasic().disable()
                    .csrf().disable()
                    .securityContext().securityContextRepository(mCookieSecurityContextRepository)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/login").anonymous()
                    .antMatchers("/api/join").anonymous()
                    .antMatchers("/api/logout").anonymous()
                    .antMatchers("/api/test").hasRole("ADMIN")
                    .antMatchers("/**").permitAll()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .formLogin()
                    .disable()
                    .addFilter(getMemberLogoutFilter())
                    .addFilter(getMemberAuthenticationFilter());

    }
    @Bean
    public MemberLogoutFilter getMemberLogoutFilter(){
        MemberLogoutFilter memberLogoutFilter = new MemberLogoutFilter("/", getMemberLogoutHandler());
        memberLogoutFilter.setFilterProcessesUrl("/api/logout");
        return memberLogoutFilter;
    }

    @Bean
    public MemberLogoutHandler getMemberLogoutHandler(){
        return new MemberLogoutHandler();
    }

    @Bean
    public MemberLoginAuthFilter getMemberAuthenticationFilter() throws Exception {
        MemberLoginAuthFilter memberLoginAuthFilter = new MemberLoginAuthFilter(getMemberProviderManager());
        memberLoginAuthFilter.setFilterProcessesUrl("/api/login");
        memberLoginAuthFilter.setAuthenticationSuccessHandler(getMemberLoginSuccessHandler());
        return memberLoginAuthFilter;
    }

/*
security는 parent provider manager가 있는데 이건 하나의 싱글빈으로 만들어진다
만약 내가 providermanager상속받아서 구현한걸 bean으로 만들면 그놈이 싱글빈되서
parent provider manager가 되버린다. 그러니까 providermanager만들떄 부모, 자식 관계를
잘 생각해서 구조를 잡고 구현하자.
 */
    private MemberProviderManager getMemberProviderManager(){
        List<AuthenticationProvider> providers = new LinkedList<AuthenticationProvider>();
        providers.add(getMemberLoginAuthProvider());
        return new MemberProviderManager(providers);
    }

    @Bean
    public MemberLoginAuthService getMemberLoginAuthService(){
        return new MemberLoginAuthService();
    }

    @Bean
    public MemberLoginAuthProvider getMemberLoginAuthProvider(){
        return new MemberLoginAuthProvider(getBCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MemberLoginSuccessHandler getMemberLoginSuccessHandler(){
        return new MemberLoginSuccessHandler();
    }
}
