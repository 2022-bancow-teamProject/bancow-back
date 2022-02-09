package com.bancow.bancowback.domain.common.exception;

import lombok.Getter;

@Getter
public class FarmQnaException extends RuntimeException {
	private ErrorCode farmQnaErrorCode;
	private String detailMessage;

	public FarmQnaException(ErrorCode farmQnaErrorCode, String detailMessage) {
		super(detailMessage);
		this.farmQnaErrorCode = farmQnaErrorCode;
		this.detailMessage = detailMessage;
	}
}
