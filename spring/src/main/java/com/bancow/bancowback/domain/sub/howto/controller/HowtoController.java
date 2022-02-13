package com.bancow.bancowback.domain.sub.howto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.sub.howto.service.HowtoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/howto")
public class HowtoController {

	private final HowtoService howtoService;

	@GetMapping()
	public Response<?> getHowto(){
		return new Response<>(howtoService.getHowto(), HttpStatus.OK);
	}
}
