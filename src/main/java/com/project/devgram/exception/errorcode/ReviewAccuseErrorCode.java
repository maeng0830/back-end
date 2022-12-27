package com.project.devgram.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReviewAccuseErrorCode implements ErrorCode {

	REVIEW_NOT_EXIST("해당 리뷰는 존재하지 않습니다."),
	ALREADY_REVIEW("해당 리뷰는 이미 신고된 상태입니다."),
	ALREADY_REVIEW_DELETE("해당 리뷰는 이미 삭제된 상태입니다."),
	REVIEW_ACCUSE_NOT_EXIST("신고된 리뷰가 없습니다."),
	NOT_EXISTENT_ACCUSE_HISTORY("신고 내역이 존재하지 않습니다.");

	private final String description;
}
