package com.bancow.bancowback.domain.main.farmqna.controller;

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
import com.bancow.bancowback.domain.main.farmqna.dto.FarmQnaAddRequestDto;
import com.bancow.bancowback.domain.main.farmqna.dto.FarmQnaDeleteRequestDto;
import com.bancow.bancowback.domain.main.farmqna.dto.FarmQnaReplyDto;
import com.bancow.bancowback.domain.main.farmqna.dto.FarmQnaResponseDto;
import com.bancow.bancowback.domain.main.farmqna.service.FarmQnaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/farmqna")
public class FarmQnaController {

	private final FarmQnaService farmQnaService;

	@PostMapping("/add")
	public Response<?> addFarmQna(@RequestBody @Valid FarmQnaAddRequestDto dto) {
		ServiceResult result = farmQnaService.addFarmQna(dto);
		return new Response<>(result, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Response<FarmQnaResponseDto> getFarmQna(@PathVariable Long id, @RequestHeader("TOKEN") String token) {
		FarmQnaResponseDto result = farmQnaService.getFarmQna(token, id);
		return new Response<>(result, HttpStatus.OK);
	}

	@GetMapping
	public Response<Page<FarmQnaResponseDto>> getFarmQnaPaging(@RequestParam int page, @RequestHeader("TOKEN") String token) {
		Page<FarmQnaResponseDto> result = farmQnaService.getFarmQnaPaging(page, token);
		return new Response<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public Response<ServiceResult> deleteFarmQna(@PathVariable Long id, @RequestHeader("TOKEN") String token) {
		ServiceResult result = farmQnaService.deleteFarmQna(token, id);
		return new Response<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public Response<ServiceResult> deleteFarmQnaList(@RequestHeader("TOKEN") final String token,
		@RequestBody @Valid final FarmQnaDeleteRequestDto dto) {
		ServiceResult result = farmQnaService.deleteFarmQnaList(token, dto);
		return new Response<>(result, HttpStatus.OK);
	}

	@PostMapping("/{id}/reply")
	public Response<ServiceResult> replyFarmQna(@RequestHeader("TOKEN") String token, @PathVariable Long id,
		@RequestBody FarmQnaReplyDto farmQnaReplyDto) {
		ServiceResult result = farmQnaService.replyFarmQna(farmQnaReplyDto, token, id);
		return new Response<>(result, HttpStatus.OK);
	}
}
