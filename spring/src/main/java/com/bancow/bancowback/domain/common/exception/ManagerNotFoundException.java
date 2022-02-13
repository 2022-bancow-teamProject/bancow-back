package com.bancow.bancowback.domain.common.exception;

import lombok.Getter;

@Getter
public class ManagerNotFoundException extends RuntimeException {
	private ErrorCode managerErrorCode;
	private String detailMessage;

	public ManagerNotFoundException(ErrorCode managerErrorCode, String detailMessage) {
		super(detailMessage);
		this.managerErrorCode = managerErrorCode;
		this.detailMessage = detailMessage;
	}

}
