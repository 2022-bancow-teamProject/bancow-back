package com.bancow.bancowback.domain.common.exception;

public class FaqException extends RuntimeException {
	private ErrorCode faqErrorCode;
	private String message;

	public FaqException(ErrorCode faqErrorCode, String message){
		super(message);
		this.faqErrorCode = faqErrorCode;
		this.message = message;
	}

}
