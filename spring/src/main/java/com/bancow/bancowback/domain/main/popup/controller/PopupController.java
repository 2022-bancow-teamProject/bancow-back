package com.bancow.bancowback.domain.main.popup.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.main.popup.dto.PopupAddRequestDto;
import com.bancow.bancowback.domain.main.popup.dto.PopupInfo;
import com.bancow.bancowback.domain.main.popup.service.PopupService;
import com.bancow.bancowback.infra.ncp.NcpService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/popup")
public class PopupController {

	private final PopupService popupService;
	private final NcpService ncpService;
	private final TokenService tokenService;

	@PostMapping("/add")
	public Response<?> addPopup(@RequestHeader("TOKEN") String token,
		@Valid @RequestPart("popup_request") PopupAddRequestDto dto,
		@RequestPart(value = "popup_image", required = false) MultipartFile popupImage) throws IOException {
		tokenService.validTokenAuthority(token);
		String ImageUploadPath = ncpService.objectUpload("popup", popupImage);
		PopupInfo popupInfo = new PopupInfo<PopupAddRequestDto>(tokenService.getManager(token), dto, ImageUploadPath);
		return new Response<>(popupService.addPopup(popupInfo), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Response<?> getPopupDetail(@RequestHeader("TOKEN") String token, @PathVariable Long id) {
		tokenService.validTokenAuthority(token);
		return new Response<>(popupService.getPopupDetail(id), HttpStatus.OK);
	}

}