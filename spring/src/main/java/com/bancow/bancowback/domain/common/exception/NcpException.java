package com.bancow.bancowback.domain.common.exception;

import lombok.Getter;

@Getter
public class NcpException extends RuntimeException {
	private ErrorCode ncpErrorCode;
	private String detailMessage;


	public NcpException(ErrorCode ncpErrorCode, String detailMessage) {
		super(detailMessage);
		this.ncpErrorCode = ncpErrorCode;
		this.detailMessage = detailMessage;
	}
}
