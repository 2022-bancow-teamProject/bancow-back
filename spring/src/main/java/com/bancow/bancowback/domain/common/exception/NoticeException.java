package com.bancow.bancowback.domain.common.exception;

public class NoticeException extends RuntimeException {
	private ErrorCode noticeErrorCode;
	private String message;

	public NoticeException(ErrorCode noticeErrorCode, String message){
		super(message);
		this.noticeErrorCode = noticeErrorCode;
		this.message = message;
	}

}
