package com.project.devgram.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryErrorCode implements ErrorCode {
	CATEGORY_NOT_EXIST("해당 카테고리는 존재하지 않습니다.");
	private final String description;

}
