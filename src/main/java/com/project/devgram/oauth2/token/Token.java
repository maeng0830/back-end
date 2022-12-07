package com.project.devgram.oauth2.token;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Token {

    private String token;
    private String refreshToken;
    @Builder
    public Token(String token, String refreshToken){
        this.token= token;
        this.refreshToken= refreshToken;
    }
}
