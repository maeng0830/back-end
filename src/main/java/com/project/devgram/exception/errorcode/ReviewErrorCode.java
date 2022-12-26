package com.project.devgram.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReviewErrorCode implements ErrorCode {

	PRODUCT_NOT_EXIST("해당 제품은 존재하지 않습니다."),
	USER_NOT_EXIST("해당 사용자는 존재하지 않습니다."),
	ALREADY_REVIEW("이미 리뷰한 제품입니다."),
	REVIEW_NOT_EXIST("해당 제품에 대한 리뷰가 없습니다.");
	private final String description;
}
