package com.bancow.bancowback.domain.common.exception;

import lombok.Getter;

@Getter
public class ManagerNotValidException extends RuntimeException {
	private ErrorCode managerErrorCode;
	private String detailMessage;

	public ManagerNotValidException(ErrorCode managerErrorCode, String detailMessage) {
		super(detailMessage);
		this.managerErrorCode = managerErrorCode;
		this.detailMessage = detailMessage;
	}

}
