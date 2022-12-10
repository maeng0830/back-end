package com.project.devgram.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {

    NOT_EXIST_TOKEN("존재하지 않는 토큰입니다."),
    ALREADY_EXIST_TOKEN("이미 존재하는 토큰입니다."),
    INVALIDATED_TOKEN("유효하지 않은 토큰입니다.");

    private final String description;


}
