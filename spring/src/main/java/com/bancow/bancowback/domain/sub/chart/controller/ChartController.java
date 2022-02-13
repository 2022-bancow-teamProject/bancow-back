package com.bancow.bancowback.domain.sub.chart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.sub.chart.dto.KoreanCowResponseDto;
import com.bancow.bancowback.domain.sub.chart.dto.QnaNumResponseDto;
import com.bancow.bancowback.domain.sub.chart.entity.KoreanCowCategory;
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

	@GetMapping("/farmqna/{year}")
	public Response<?> farmQnaYear(@RequestHeader("TOKEN") String token, @PathVariable int year) {
		List<Map<String, Object>> maps = chartService.farmQnaYear(token, year);
		return new Response<>(maps, HttpStatus.OK);
	}

	@GetMapping("/qna/{year}")
	public Response<?> qnaYear(@RequestHeader("TOKEN") String token, @PathVariable int year) {
		List<Map<String, Object>> maps = chartService.qnaYear(token, year);
		return new Response<>(maps, HttpStatus.OK);
	}

	@GetMapping("/koreancow/{category}")
	public Response<?> koreanCow(@PathVariable KoreanCowCategory category) {
		KoreanCowResponseDto result = chartService.koreanCow(category);
		return new Response<>(result, HttpStatus.OK);
	}

}
