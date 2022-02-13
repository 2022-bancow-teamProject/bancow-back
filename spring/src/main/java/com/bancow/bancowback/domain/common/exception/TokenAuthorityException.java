package com.bancow.bancowback.domain.common.exception;

public class TokenAuthorityException extends TokenNotFoundException {

	public TokenAuthorityException(ErrorCode tokenErrorCode, String detailMessage) {
		super(tokenErrorCode, detailMessage);
	}
}
