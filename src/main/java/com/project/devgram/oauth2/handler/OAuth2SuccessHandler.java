package com.project.devgram.oauth2.handler;

import com.project.devgram.dto.UserDto;
import com.project.devgram.dto.UserRequestMapper;
import com.project.devgram.oauth2.token.Token;
import com.project.devgram.oauth2.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {


    private final TokenService tokenService;
    private final UserRequestMapper mapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserDto userDto = mapper.toDto(oAuth2User);
        log.info("userDto {}", userDto.getUsername());

        //토큰 생성
        Token token = tokenService.generateToken(userDto.getUsername(), "ROLE_USER");

        log.info("토큰 내꺼{} ", token);

        String uri = UriComponentsBuilder.fromUriString("http://localhost:8080/api/oauth/redirect")
                .queryParam("token", token.getToken())
                .queryParam("refresh",token.getRefreshToken())
                .build().toUriString();


        response.sendRedirect(uri);


    }

}
