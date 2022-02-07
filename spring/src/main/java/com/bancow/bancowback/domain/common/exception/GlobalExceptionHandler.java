package com.bancow.bancowback.domain.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("handleMethodArgumentNotValidException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException e) {
		log.error("handleMethodArgumentTypeMismatchException", e);
		final ErrorResponse response = ErrorResponse.of(e);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException e) {
		log.error("handleHttpRequestMethodNotSupportedException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
		return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e) {
		log.error("handleEntityNotFoundException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NcpException.class)
	protected ResponseEntity<ErrorResponse> handleNcpException(NcpException e) {
		log.error("handleNcpException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_IMAGE);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(QnaException.class)
	protected ResponseEntity<ErrorResponse> handleQnaException(QnaException e) {
		log.error("qnaException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_Found_QNA);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(PopupException.class)
	protected ResponseEntity<ErrorResponse> handlePopupException(PopupException e) {
		log.error("PopupException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_FOUND_POPUP);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

