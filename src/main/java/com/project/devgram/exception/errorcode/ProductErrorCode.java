package com.project.devgram.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductErrorCode implements ErrorCode {
	CANNOT_FIND_PRODUCT_BY_PRODUCT_SEQ("productSeq 에 해당하는 product 를 찾을수 없습니다.");
	private final String description;
}
