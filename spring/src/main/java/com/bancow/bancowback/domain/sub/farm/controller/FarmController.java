package com.bancow.bancowback.domain.sub.farm.controller;

import java.io.IOException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.sub.farm.dto.FarmAddRequestDto;
import com.bancow.bancowback.domain.sub.farm.dto.FarmUpdateRequestDto;
import com.bancow.bancowback.domain.sub.farm.entity.FarmInfo;
import com.bancow.bancowback.domain.sub.farm.mapper.FarmMapper;
import com.bancow.bancowback.domain.sub.farm.service.FarmService;
import com.bancow.bancowback.infra.ncp.NcpService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/farm")
public class FarmController {

	private final FarmService farmService;
	private final NcpService ncpService;
	private final TokenService tokenService;
	private final FarmMapper farmMapper;

	@PostMapping("/add")
	public Response<?> addFarmInfo(@RequestHeader("TOKEN") String token,
		@Valid @RequestPart("farm_request") final FarmAddRequestDto dto,
		@RequestPart(value = "farm_image", required = false) final MultipartFile farmImage,
		@RequestPart(value = "farm_ceo_image", required = false) final MultipartFile farmCeoImage)
		throws IOException {
		tokenService.validTokenAuthority(token);
		String farmImageUploadPath = ncpService.objectUpload("farm", farmImage);
		String farmCEOImageUploadPath = ncpService.objectUpload("farm", farmCeoImage);
		FarmAddRequestDto farmAddRequestDto = farmMapper.toFarmAddRequestDto(dto, farmImageUploadPath,
			farmCEOImageUploadPath);
		FarmInfo farmInfo = farmMapper.toFarmInfo(tokenService.getManager(token), farmAddRequestDto);
		return new Response<>(farmService.addFarm(farmInfo), HttpStatus.OK);
	}

	@GetMapping("/distribute")
	public Response<?> getFarmDistribute() {
		return new Response<>(farmService.getFarmDistribute(), HttpStatus.OK);
	}

	@GetMapping()
	public Response<?> getFarmPaging(@RequestHeader("TOKEN") final String token,
		@NotNull @RequestParam("page") final int page) {
		tokenService.validTokenAuthority(token);
		return new Response<>(farmService.getFarmPaging(page), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Response<?> getFarmDetail(@RequestHeader("TOKEN") final String token,
		@NotNull @PathVariable final Long id) {
		tokenService.validTokenAuthority(token);
		return new Response<>(farmService.getFarmDetail(id), HttpStatus.OK);
	}

	@PostMapping("/edit")
	public Response<?> editFarmImage(@RequestHeader("TOKEN") final String token,
		@Valid @RequestPart("farm_request") FarmUpdateRequestDto FarmUpdateRequestDto,
		@RequestPart(value = "farm_image", required = false) final MultipartFile farmImage,
		@RequestPart(value = "farm_ceo_image", required = false) final MultipartFile farmCeoImage) throws IOException {
		tokenService.validTokenAuthority(token);
		String farmImageUploadPath = ncpService.objectUpload("farm", farmImage);
		String farmCEOImageUploadPath = ncpService.objectUpload("farm", farmCeoImage);
		FarmUpdateRequestDto farmUpdateRequestDto = farmMapper.toFarmUpdateRequestDto(FarmUpdateRequestDto, farmImageUploadPath,
			farmCEOImageUploadPath);
		FarmInfo farmInfo = farmMapper.toUpdateFarmInfo(tokenService.getManager(token),farmUpdateRequestDto);
		return new Response<>(farmService.editFarmImage(farmInfo), HttpStatus.OK);
	}

}
