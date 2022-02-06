package com.bancow.bancowback.domain.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE("C001", "Invalid Input Value"),
	METHOD_NOT_ALLOWED( "C002", "METHOD_NOT_ALLOWED"),
	INTERNAL_SERVER_ERROR("C003", "Server Error"),
	INVALID_TYPE_VALUE( "C004",  "Invalid Type Value"),
	;
	private final String code;
	private final String message;


	ErrorCode( final String code, final String message) {
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}
	public String getCode() {
		return code;
	}
}