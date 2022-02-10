package com.bancow.bancowback.domain.main.farmqna.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import com.bancow.bancowback.domain.main.farmqna.entity.FarmQna;
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
	public Response<FarmQna> getFarmQna(@PathVariable Long id, @RequestHeader("TOKEN") String token) {
		FarmQna farmQna = farmQnaService.getFarmQna(token, id);
		return new Response<>(farmQna, HttpStatus.OK);
	}

	@GetMapping
	public Response<Page<FarmQna>> getFarmQnaPaging(@RequestParam int page, @RequestHeader("TOKEN") String token) {
		Page<FarmQna> pagingFarmQna = farmQnaService.getFarmQnaPaging(page, token);
		return new Response<>(pagingFarmQna, HttpStatus.OK);
	}
}
