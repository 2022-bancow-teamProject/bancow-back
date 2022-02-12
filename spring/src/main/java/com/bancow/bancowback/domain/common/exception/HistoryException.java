package com.bancow.bancowback.domain.common.exception;

public class HistoryException extends RuntimeException {
	private ErrorCode historyErrorCode;
	private String message;

	public HistoryException(ErrorCode historyErrorCode, String message){
		super(message);
		this.historyErrorCode = historyErrorCode;
		this.message = message;
	}

}
