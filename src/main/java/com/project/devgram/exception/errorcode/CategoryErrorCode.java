package com.project.devgram.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryErrorCode implements ErrorCode {
	CANNOT_FIND_CATEGORY_BY_CATEGORY_SEQ("categorySeq 에 해당하는 category 를 찾을수 없습니다.");
	private final String description;
}
