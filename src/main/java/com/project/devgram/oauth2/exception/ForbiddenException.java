package com.project.devgram.oauth2.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message){
        super(message);
    }

}
