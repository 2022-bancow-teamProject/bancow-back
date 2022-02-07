package com.bancow.bancowback.domain.common.exception;

import lombok.Getter;

@Getter
public class QnaException extends RuntimeException {
	private ErrorCode qnaErrorCode;
	private String detailMessage;

	public QnaException(ErrorCode qnaErrorCode, String detailMessage) {
		super(detailMessage);
		this.qnaErrorCode = qnaErrorCode;
		this.detailMessage = detailMessage;
	}

}
