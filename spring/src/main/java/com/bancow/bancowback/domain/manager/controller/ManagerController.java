package com.bancow.bancowback.domain.manager.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.manager.dto.ManagerFindDto;
import com.bancow.bancowback.domain.manager.dto.ManagerLoginDto;
import com.bancow.bancowback.domain.manager.dto.ManagerLoginResultDto;
import com.bancow.bancowback.domain.manager.dto.ManagerPasswordDto;
import com.bancow.bancowback.domain.manager.dto.ManagerRegisterDto;
import com.bancow.bancowback.domain.manager.dto.ManagerResponseDto;
import com.bancow.bancowback.domain.manager.service.ManagerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ManagerController {

	private final ManagerService managerService;

	@PostMapping("/register")
	public Response<ServiceResult> registerManager(@RequestBody @Valid ManagerRegisterDto managerRegisterDto) {
		ServiceResult result = managerService.registerManager(managerRegisterDto);
		return new Response<>(result, HttpStatus.OK);
	}

	@PostMapping("/login")
	public Response<ManagerLoginResultDto> login(@RequestBody @Valid ManagerLoginDto managerLoginDto) {
		ManagerLoginResultDto result = managerService.loginManager(managerLoginDto);
		return new Response<>(result, HttpStatus.OK);
	}

	@PostMapping("/logout")
	public Response<ServiceResult> logout(@RequestHeader("TOKEN") String token) {
		ServiceResult result = managerService.logoutManager(token);
		return new Response<>(result, HttpStatus.OK);
	}

	@GetMapping("/authentication/{token}")
	public Response<ServiceResult> authentication(@PathVariable String token) {
		ServiceResult result = managerService.authentication(token);
		return new Response<>(result, HttpStatus.OK);
	}

	@PatchMapping("/statusadmin")
	public Response<ServiceResult> statusToAdmin(@RequestHeader("TOKEN") String token, @RequestParam Long id) {
		ServiceResult result = managerService.statusToAdmin(token, id);
		return new Response<>(result, HttpStatus.OK);
	}

	@GetMapping("/allmanager")
	public Response<Page<ManagerResponseDto>> findPagingManager(@RequestParam int page, @RequestHeader("TOKEN") String token) {
		Page<ManagerResponseDto> pagingManager = managerService.findPagingManager(page, token);
		return new Response<>(pagingManager, HttpStatus.OK);
	}

	@PostMapping("/findmanager")
	public Response<ServiceResult> findManager(@RequestBody @Valid ManagerFindDto managerFindDto) {
		ServiceResult result = managerService.findManager(managerFindDto);
		return new Response<>(result, HttpStatus.OK);
	}

	@GetMapping("/authentication/findmanager/{token}")
	public Response<ServiceResult> authenticationPassword(@PathVariable String token) {
		ServiceResult result = managerService.authenticationPassword(token);
		return new Response<>(result, HttpStatus.OK);
	}

	@PatchMapping("/authentication/findmanager/{token}/change-password")
	public Response<ServiceResult> changePassword(@PathVariable String token,
		@RequestBody @Valid ManagerPasswordDto managerPasswordDto) {
		ServiceResult result = managerService.changePassword(token, managerPasswordDto);
		return new Response<>(result, HttpStatus.OK);
	}
}
