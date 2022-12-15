package com.project.devgram.config;


import com.project.devgram.oauth2.handler.OAuth2AuthenticationFailureHandler;
import com.project.devgram.oauth2.handler.OAuth2SuccessHandler;
import com.project.devgram.oauth2.principal.PrincipalDetailsService;
import com.project.devgram.oauth2.principal.PrincipalOauth2UserService;
import com.project.devgram.oauth2.token.JwtAuthFilter;
import com.project.devgram.oauth2.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

   private final PrincipalOauth2UserService principalOauth2UserService;
    private final PrincipalDetailsService principalDetailsService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    private final CorsConfig corsConfig;
    private final TokenService tokenService;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsConfig.corsFilter())
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/api/user/**").authenticated()
                .antMatchers("/api/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and().logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login().defaultSuccessUrl("/") // loginForm 삭제 1212
                .successHandler(oAuth2SuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

        http.addFilterBefore(new JwtAuthFilter(tokenService,principalDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring()
                .mvcMatchers("**/favicon.ico","/cdn**","/maxcdn**","/jquery**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    //@crossOrigin은 인증이 필요한 요청을 무시
}
