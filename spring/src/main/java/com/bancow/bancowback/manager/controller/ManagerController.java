package com.bancow.bancowback.manager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.common.dto.Response;
import com.bancow.bancowback.common.dto.ServiceResult;
import com.bancow.bancowback.manager.dto.ManagerDto;
import com.bancow.bancowback.manager.dto.ManagerFindDto;
import com.bancow.bancowback.manager.dto.ManagerLoginDto;
import com.bancow.bancowback.manager.dto.ManagerRegisterDto;
import com.bancow.bancowback.manager.entity.Manager;
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

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid ManagerLoginDto managerLoginDto) {
		ServiceResult result = managerService.loginManager(managerLoginDto);
		return ResponseEntity.ok().body(new Response<>(result, HttpStatus.OK));
	}

	@GetMapping("/authentication/{token}")
	public ResponseEntity<?> authentication(@PathVariable String token) {
		ServiceResult result = managerService.authentication(token);
		return ResponseEntity.ok().body(new Response<>(result, HttpStatus.OK));
	}

	@PatchMapping("/status-to-admin")
	public ResponseEntity<?> statusToAdmin(@RequestHeader("TOKEN") String token, @RequestParam Long id) {
		ServiceResult result = managerService.statusToAdmin(token, id);
		return ResponseEntity.ok().body(new Response<>(result, HttpStatus.OK));
	}

	@GetMapping("/allManager")
	public ResponseEntity<?> findAllManager(@RequestHeader("TOKEN") String token) {
		List<ManagerDto> allManager = managerService.findAllManager(token);
		return ResponseEntity.ok().body(new Response<>(allManager, HttpStatus.OK));
	}

	@PostMapping("/find-manager")
	public ResponseEntity<?> findManager(@RequestBody @Valid ManagerFindDto managerFindDto) {
		ServiceResult result = managerService.findManager(managerFindDto);
		return ResponseEntity.ok().body(new Response<>(result, HttpStatus.OK));
	}
}
