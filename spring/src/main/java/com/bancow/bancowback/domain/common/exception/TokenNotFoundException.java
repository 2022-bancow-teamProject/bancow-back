package com.bancow.bancowback.domain.common.exception;

import lombok.Getter;

@Getter
public class TokenNotFoundException extends RuntimeException {
	private ErrorCode tokenErrorCode;
	private String detailMessage;

	public TokenNotFoundException(ErrorCode tokenErrorCode, String detailMessage) {
		super(detailMessage);
		this.tokenErrorCode = tokenErrorCode;
		this.detailMessage = detailMessage;
	}

}
