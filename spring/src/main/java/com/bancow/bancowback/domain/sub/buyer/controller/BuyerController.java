package com.bancow.bancowback.domain.sub.buyer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.domain.common.dto.Response;

import com.bancow.bancowback.domain.sub.buyer.service.BuyerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buyer")
public class BuyerController {
	private final BuyerService buyerService;

	@GetMapping("/distribute")
	public Response<?> getBuyerDistribute() {
		return new Response<>(buyerService.getBuyerDistribute(), HttpStatus.OK);
	}

}
