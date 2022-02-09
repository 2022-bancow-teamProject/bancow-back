package com.bancow.bancowback.domain.main.farmqna.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.main.farmqna.dto.FarmQnaAddRequestDto;
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
}
