package com.bancow.bancowback.domain.sub.buyer.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.domain.common.dto.Response;

import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.sub.buyer.dto.BuyerUpdateRequestDto;
import com.bancow.bancowback.domain.sub.buyer.service.BuyerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buyer")
public class BuyerController {
	private final BuyerService buyerService;
	private final TokenService tokenService;

	@GetMapping("/distribute")
	public Response<?> getBuyerDistribute() {
		return new Response<>(buyerService.getBuyerDistribute(), HttpStatus.OK);
	}

	@PatchMapping("/edit")
	public Response<?> editBuyer(@RequestHeader("TOKEN") final String token,
		@Valid @RequestBody BuyerUpdateRequestDto buyerUpdateRequestDto) {
		tokenService.validTokenAuthority(token);
		return new Response<>(buyerService.editBuyer(buyerUpdateRequestDto), HttpStatus.OK);
	}
}
