package com.bancow.bancowback.domain.sub.chart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.sub.chart.dto.QnaNumResponseDto;
import com.bancow.bancowback.domain.sub.chart.service.ChartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chart")
public class ChartController {

	private final ChartService chartService;

	@GetMapping("/qna")
	public Response<?> qnaNum(@RequestHeader("TOKEN") String token) {
		QnaNumResponseDto result = chartService.qnaNum(token);
		return new Response<>(result, HttpStatus.OK);
	}
}
