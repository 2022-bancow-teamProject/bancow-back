package com.bancow.bancowback.domain.common.exception;

import lombok.Getter;

@Getter
public class RegisterNotEqualPasswordException extends RuntimeException {
	private ErrorCode registerErrorCode;
	private String detailMessage;

	public RegisterNotEqualPasswordException(ErrorCode registerErrorCode, String detailMessage) {
		super(detailMessage);
		this.registerErrorCode = registerErrorCode;
		this.detailMessage = detailMessage;
	}

}
