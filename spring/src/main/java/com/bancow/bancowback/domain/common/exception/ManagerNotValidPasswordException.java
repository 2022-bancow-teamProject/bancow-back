package com.bancow.bancowback.domain.common.exception;

import lombok.Getter;

@Getter
public class ManagerNotValidPasswordException extends RuntimeException {
	private ErrorCode managerErrorCode;
	private String detailMessage;

	public ManagerNotValidPasswordException(ErrorCode managerErrorCode, String detailMessage) {
		super(detailMessage);
		this.managerErrorCode = managerErrorCode;
		this.detailMessage = detailMessage;
	}

}
