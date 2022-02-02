package com.bancow.bancowback.manager.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.common.dto.Response;
import com.bancow.bancowback.common.dto.ServiceResult;
import com.bancow.bancowback.manager.dto.ManagerRegisterDto;
import com.bancow.bancowback.manager.service.ManagerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ManagerController {

	private final ManagerService managerService;

	@PostMapping("/register")
	public ResponseEntity<?> registerManager(@RequestBody @Valid ManagerRegisterDto managerRegisterDto) {
		ServiceResult result = managerService.registerManager(managerRegisterDto);
		return ResponseEntity.ok().body(new Response<>(result, HttpStatus.OK));
	}

}
