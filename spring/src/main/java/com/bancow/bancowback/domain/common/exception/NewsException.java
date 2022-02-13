package com.bancow.bancowback.domain.common.exception;

public class NewsException extends RuntimeException {
	private ErrorCode ErrorCode;
	private String detailMessage;

	public NewsException(ErrorCode ErrorCode, String detailMessage) {
		super(detailMessage);
		this.ErrorCode = ErrorCode;
		this.detailMessage = detailMessage;
	}
}
