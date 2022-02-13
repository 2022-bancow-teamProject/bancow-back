package com.bancow.bancowback.domain.common.exception;

public class TokenAuthoritySuperException extends TokenNotFoundException {

	public TokenAuthoritySuperException(ErrorCode tokenErrorCode, String detailMessage) {
		super(tokenErrorCode, detailMessage);
	}
}
