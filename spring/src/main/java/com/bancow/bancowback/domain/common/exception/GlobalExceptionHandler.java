package com.bancow.bancowback.domain.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.bancow.bancowback.domain.sub.news.entity.News;

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

	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<ErrorResponse> handlerMissingRequestParamException(MissingServletRequestParameterException e) {
		log.error("MissingServletRequestParameterException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.MISSING_REQUEST_PARAMETER);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FarmQnaException.class)
	protected ResponseEntity<ErrorResponse> handlerFarmQnaException(FarmQnaException e) {
		log.error("FarmQnaException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_FOUND_FARM_QNA);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(EventException.class)
	protected ResponseEntity<ErrorResponse> handlerEventException(EventException e) {
		log.error("EventException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_FOUND_EVENT);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FarmException.class)
	protected ResponseEntity<ErrorResponse> handlerFarmException(FarmException e) {
		log.error("FarmException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_FOUND_FARM);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NoticeException.class)
	protected ResponseEntity<ErrorResponse> handlePopupException(NoticeException e) {
		log.error("NoticeException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_FOUND_NOTICE);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(BuyerException.class)
	protected ResponseEntity<ErrorResponse> handlerFarmException(BuyerException e) {
		log.error("BuyerException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_FOUND_BUYER);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FaqException.class)
	protected ResponseEntity<ErrorResponse> handlePopupException(FaqException e) {
		log.error("FaqException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_FOUND_FAQ);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NewsException.class)
	protected ResponseEntity<ErrorResponse> handlerNewsException(NewsException e) {
		log.error("NewsException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_FOUND_NEWS);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

