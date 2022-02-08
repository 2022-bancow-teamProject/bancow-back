package com.bancow.bancowback.domain.main.qna.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.main.qna.entity.Qna;
import com.bancow.bancowback.domain.main.qna.service.QnaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class QnaController {

	private final QnaService qnaService;

	@GetMapping("/{id}")
	public Response<Qna> getQna(@PathVariable Long id, @RequestHeader("TOKEN") String token) {
		Qna qna = qnaService.getQna(token, id);
		return new Response<>(qna, HttpStatus.OK);
	}

	@GetMapping
	public Response<Page<Qna>> getAllQna(@RequestParam int page, @RequestHeader("TOKEN") String token) {
		Page<Qna> allQna = qnaService.getAllQna(page, token);
		return new Response<>(allQna, HttpStatus.OK);
	}
}
