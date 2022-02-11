package com.bancow.bancowback.domain.main.event.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.main.event.dto.EventAddRequestDto;
import com.bancow.bancowback.domain.main.event.dto.EventInfo;
import com.bancow.bancowback.domain.main.event.service.EventService;

import com.bancow.bancowback.infra.ncp.NcpService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event")
public class EventController {

	private final EventService eventService;
	private final NcpService ncpService;
	private final TokenService tokenService;

	@PostMapping("add")
	public Response<?> addEvent(@RequestHeader("TOKEN") String token,
		@Valid @RequestPart("event_request") final EventAddRequestDto dto,
		@RequestPart(value = "event_image", required = false) final MultipartFile eventImage) throws IOException {
		String ImageUploadPath = ncpService.objectUpload("event", eventImage);
		EventInfo eventInfo = new EventInfo<EventAddRequestDto>(tokenService.getManager(token), dto, ImageUploadPath);
		return new Response<>(eventService.addEvent(eventInfo), HttpStatus.OK);
	}

	@GetMapping("/distribute")
	public Response<?> getPopupDistribute() {
		return new Response<>(eventService.getEventDistribute(), HttpStatus.OK);
	}

}
