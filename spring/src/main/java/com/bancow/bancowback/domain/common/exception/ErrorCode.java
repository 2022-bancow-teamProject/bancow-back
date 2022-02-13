package com.bancow.bancowback.domain.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE("C001", "Invalid Input Value"),
	METHOD_NOT_ALLOWED( "C002", "METHOD_NOT_ALLOWED"),
	INTERNAL_SERVER_ERROR("C003", "Server Error"),
	INVALID_TYPE_VALUE( "C004",  "Invalid Type Value"),
	MISSING_REQUEST_PARAMETER("C005", "Missing Required Request Parameter"),

	// Ncp
	NOT_IMAGE("N001", "Not Image file"),
	NOT_S3_ERROR("N002","NOT_S3_ERROR"),

	// Qna
	NOT_Found_QNA("Q001", "Not Found Qna"),

	// FarmQna
	NOT_FOUND_FARM_QNA("FQ001", "Not Found Farm Qna"),

	// Popup
	NOT_FOUND_POPUP("P001", "Not Found Popup"),

	// Notice
	NOT_FOUND_NOTICE("T001", "Not Found Notice"),

	// Faq
	NOT_FOUND_FAQ("G001", "Not Found Faq"),

	// History
	NOT_FOUND_HISTORY("H001", "Not Found History"),

	// Event
	NOT_FOUND_EVENT("E001", "Not Found Event"),

	// Farm
	NOT_FOUND_FARM("F001", "Not Found Farm"),

	//Buyer
	NOT_FOUND_BUYER("B001", "Not Found Buyer"),

	// NEWS
	NOT_FOUND_NEWS("N001", "Not Found News"),
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