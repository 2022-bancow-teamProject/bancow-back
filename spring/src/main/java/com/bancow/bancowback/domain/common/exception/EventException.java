package com.bancow.bancowback.domain.common.exception;

import lombok.Getter;

@Getter
public class EventException extends RuntimeException {
	private ErrorCode ErrorCode;
	private String detailMessage;


	public EventException(ErrorCode ErrorCode, String detailMessage) {
		super(detailMessage);
		this.ErrorCode = ErrorCode;
		this.detailMessage = detailMessage;
	}
}
