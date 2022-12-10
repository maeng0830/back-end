package com.project.devgram.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_EXIST("해당 유저가 존재하지 않습니다."),
    USER_ALREADY_EXIST("이미 존재하는 유저 입니다.");

    private final String description;


}
