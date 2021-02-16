package ml.market.cors.config.security;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.security.oauth.exception.AccessDeniedHandlerImpl;
import ml.market.cors.domain.security.oauth.exception.ExceptionPoint;
import ml.market.cors.domain.security.CookieSecurityContextRepository;
import ml.market.cors.domain.security.ParentProviderManager;
import ml.market.cors.domain.security.member.filter.MemberLoginAuthFilter;

import java.util.List;

import ml.market.cors.domain.security.member.filter.MemberLogoutFilter;
import ml.market.cors.domain.security.member.handler.MemberLoginSuccessHandler;
import ml.market.cors.domain.security.member.handler.MemberLogoutHandler;
import ml.market.cors.domain.security.member.manager.MemberProviderManager;
import ml.market.cors.domain.security.member.provider.MemberLoginAuthProvider;
import ml.market.cors.domain.security.member.service.MemberLoginAuthService;
import ml.market.cors.domain.security.oauth.handler.OauthSuccessHandler;
import ml.market.cors.domain.security.oauth.provider.CustomOAuth2Provider;
import ml.market.cors.domain.security.oauth.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;

import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CookieSecurityContextRepository mCookieSecurityContextRepository;

    private final OauthSuccessHandler oauthSuccessHandler;

    private final ExceptionPoint exceptionPoint;

    private final AccessDeniedHandlerImpl accessDeniedHandler;

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
                    .headers()
                        .frameOptions().sameOrigin()
                    .and()
                    .securityContext().securityContextRepository(mCookieSecurityContextRepository)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/login").anonymous()
                    .antMatchers("/api/join").anonymous()
                    .antMatchers("/api/logout").authenticated()
                    .antMatchers("/api/market/view").authenticated()
                    .antMatchers("/api/market/update").authenticated()
                    .antMatchers("/api/market/save").authenticated()
                    .antMatchers("/api/mypage/**").authenticated()
                    .antMatchers("/api/change/profile").authenticated()
                    .antMatchers("/**").permitAll()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .oauth2Login()
                    .loginPage("/api/oauth2")
                    .authorizationEndpoint()
                    .baseUri("/api/oauth2/authorization")
                    .and()
                    .redirectionEndpoint()
                    .baseUri("/api/login/oauth2/code/*")
                    .and()
                    .userInfoEndpoint().userService(new CustomOAuth2UserService())
                    .and()
                    .successHandler(oauthSuccessHandler)
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(exceptionPoint)
                    .accessDeniedHandler(accessDeniedHandler)
                    .and()
                    .formLogin()
                    .disable()
                    .addFilter(getMemberLogoutFilter())
                    .addFilter(getMemberAuthenticationFilter());
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            OAuth2ClientProperties oAuth2ClientProperties,
            @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId,
            @Value("${custom.oauth2.kakao.client-secret}") String kakaoClientSecret,
            @Value("${custom.oauth2.naver.client-id}") String naverClientId,
            @Value("${custom.oauth2.naver.client-secret}") String naverClientSecret) {
        List<ClientRegistration> registrations = oAuth2ClientProperties
                .getRegistration().keySet().stream()
                .map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret(kakaoClientSecret)
                .jwkSetUri("temp")
                .build());

        registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
                .clientId(naverClientId)
                .clientSecret(naverClientSecret)
                .jwkSetUri("temp")
                .build());
        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
        if("google".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .redirectUriTemplate(CustomOAuth2Provider.DEFAULT_LOGIN_REDIRECT_URL)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile")
                    .build();
        }
        return null;
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
