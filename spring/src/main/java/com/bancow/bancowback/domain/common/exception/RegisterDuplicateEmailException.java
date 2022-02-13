package com.bancow.bancowback.domain.common.exception;

import lombok.Getter;

@Getter
public class RegisterDuplicateEmailException extends RuntimeException {
	private ErrorCode registerErrorCode;
	private String detailMessage;

	public RegisterDuplicateEmailException(ErrorCode registerErrorCode, String detailMessage) {
		super(detailMessage);
		this.registerErrorCode = registerErrorCode;
		this.detailMessage = detailMessage;
	}

}
