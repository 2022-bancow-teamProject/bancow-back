package com.bancow.bancowback.domain.main.qna.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.main.qna.dto.QnaDeleteRequestDto;
import com.bancow.bancowback.domain.main.qna.dto.QnaReplyDto;
import com.bancow.bancowback.domain.main.qna.dto.QnaRequestDto;
import com.bancow.bancowback.domain.main.qna.dto.QnaResponseDto;
import com.bancow.bancowback.domain.main.qna.service.QnaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class QnaController {

	private final QnaService qnaService;

	@GetMapping("/{id}")
	public Response<QnaResponseDto> getQna(@PathVariable Long id, @RequestHeader("TOKEN") String token) {
		QnaResponseDto dto = qnaService.getQna(token, id);
		return new Response<>(dto, HttpStatus.OK);
	}

	@GetMapping
	public Response<Page<QnaResponseDto>> getQnaPaging(@RequestParam int page, @RequestHeader("TOKEN") String token) {
		Page<QnaResponseDto> pagingQna = qnaService.getQnaPaging(page, token);
		return new Response<>(pagingQna, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public Response<?> deleteQna(@PathVariable Long id, @RequestHeader("TOKEN") String token) {
		ServiceResult result = qnaService.deleteQna(token, id);
		return new Response<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public Response<?> deleteQnaList(@RequestHeader("TOKEN") String token,
		@RequestBody @Valid QnaDeleteRequestDto dto) {
		ServiceResult result = qnaService.deleteQnaList(token, dto);
		return new Response<>(result, HttpStatus.OK);
	}

	@PostMapping("/add")
	public Response<ServiceResult> addQna(@RequestBody @Valid QnaRequestDto qnaRequestDto) {
		ServiceResult result = qnaService.addQna(qnaRequestDto);
		return new Response<>(result, HttpStatus.OK);
	}

	@PostMapping("/{id}/reply")
	public Response<ServiceResult> replyQna(@RequestHeader("TOKEN") String token, @PathVariable Long id,
		@RequestBody QnaReplyDto dto) {
		ServiceResult result = qnaService.replyQna(dto, token, id);
		return new Response<>(result, HttpStatus.OK);
	}
}
