package com.bancow.bancowback.domain.main.popup.controller;

import java.io.IOException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.main.popup.dto.PopupAddRequestDto;
import com.bancow.bancowback.domain.main.popup.dto.PopupInfo;
import com.bancow.bancowback.domain.main.popup.dto.PopupResponseDto;
import com.bancow.bancowback.domain.main.popup.dto.PopupUpdateRequestDto;
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
		@Valid @RequestPart("popup_request") final PopupAddRequestDto dto,
		@RequestPart(value = "popup_image", required = false) final MultipartFile popupImage) throws IOException {
		tokenService.validTokenAuthority(token);
		String ImageUploadPath = ncpService.objectUpload("popup", popupImage);
		PopupInfo popupInfo = new PopupInfo<PopupAddRequestDto>(tokenService.getManager(token), dto, ImageUploadPath);
		return new Response<>(popupService.addPopup(popupInfo), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Response<?> getPopupDetail(@RequestHeader("TOKEN") final String token,
		@NotNull @PathVariable final Long id) {
		tokenService.validTokenAuthority(token);
		return new Response<>(popupService.getPopupDetail(id), HttpStatus.OK);
	}

	@GetMapping()
	public Response<?> getPopupPaging(@RequestHeader("TOKEN") final String token,
		@NotNull @RequestParam("page") final int page) {
		tokenService.validTokenAuthority(token);
		Page<PopupResponseDto> result = popupService.getPopupPaging(page);
		return new Response<>(result, HttpStatus.OK);
	}

	@PostMapping("/edit")
	public Response<?> editPopupImage(@RequestHeader("TOKEN") final String token,
		@Valid @RequestPart("popup_request") PopupUpdateRequestDto dto,
		@RequestPart(value = "popup_image", required = false) MultipartFile popupImage) throws IOException {
		tokenService.validTokenAuthority(token);
		String ImageUploadPath = ncpService.objectUpload("popup", popupImage);
		PopupInfo popupInfo = new PopupInfo<PopupUpdateRequestDto>(tokenService.getManager(token), dto,
			ImageUploadPath);
		return new Response<>(popupService.editPopupImage(popupInfo), HttpStatus.OK);
	}

	@PatchMapping("/edit")
	public Response<?> editPopupNotImage(@RequestHeader("TOKEN") final String token,
		@Valid @RequestBody PopupUpdateRequestDto popupDto) {
		tokenService.validTokenAuthority(token);
		return new Response<>(popupService.editPopupNotImage(popupDto), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public Response<?> deletePopupOne(@RequestHeader("TOKEN") final String token, @PathVariable Long id) {
		tokenService.validTokenAuthority(token);
		return new Response<>(popupService.deletePopupOne(id), HttpStatus.OK);
	}

}