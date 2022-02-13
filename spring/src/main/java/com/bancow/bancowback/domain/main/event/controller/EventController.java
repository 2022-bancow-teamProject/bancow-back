package com.bancow.bancowback.domain.main.event.controller;

import java.io.IOException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import com.bancow.bancowback.domain.main.event.dto.EventAddRequestDto;
import com.bancow.bancowback.domain.main.event.dto.EventDeleteRequestDto;
import com.bancow.bancowback.domain.main.event.dto.EventInfo;
import com.bancow.bancowback.domain.main.event.dto.EventUpdateRequestDto;
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

	@PostMapping("/add")
	public Response<?> addEvent(@RequestHeader("TOKEN") String token,
		@Valid @RequestPart("event_request") final EventAddRequestDto dto,
		@RequestPart(value = "event_image", required = false) final MultipartFile eventImage) throws IOException {
		String ImageUploadPath = ncpService.objectUpload("event", eventImage);
		EventInfo eventInfo = new EventInfo<EventAddRequestDto>(tokenService.getManager(token), dto, ImageUploadPath);
		return new Response<>(eventService.addEvent(eventInfo), HttpStatus.OK);
	}

	@GetMapping("/distribute")
	public Response<?> getEventDistribute(@RequestParam("status") final Boolean status) {
		return new Response<>(eventService.getEventDistribute(status), HttpStatus.OK);
	}

	@GetMapping()
	public Response<?> getEventPaging(@RequestHeader("TOKEN") final String token,
		@NotNull @RequestParam("page") final int page) {
		tokenService.validTokenAuthority(token);
		return new Response<>(eventService.getEventPaging(page), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Response<?> getEventDetail(@RequestHeader("TOKEN") final String token,
		@NotNull @PathVariable final Long id) {
		tokenService.validTokenAuthority(token);
		return new Response<>(eventService.getEventDetail(id), HttpStatus.OK);
	}
	@PostMapping("/edit")
	public Response<?> editEventImage(@RequestHeader("TOKEN") final String token,
		@Valid @RequestPart("event_request") EventUpdateRequestDto dto,
		@RequestPart(value = "event_image", required = false) MultipartFile eventImage) throws IOException {
		tokenService.validTokenAuthority(token);
		String ImageUploadPath = ncpService.objectUpload("event", eventImage);
		EventInfo eventInfo = new EventInfo<EventUpdateRequestDto>(tokenService.getManager(token), dto,
			ImageUploadPath);
		return new Response<>(eventService.editEventImage(eventInfo), HttpStatus.OK);
	}

	@PatchMapping("/edit")
	public Response<?> editEventNotImage(@RequestHeader("TOKEN") final String token,
		@Valid @RequestBody EventUpdateRequestDto EventUpdateRequestDto) {
		tokenService.validTokenAuthority(token);
		return new Response<>(eventService.editEventNotImage(tokenService.getManager(token),EventUpdateRequestDto), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public Response<?> deleteEventOne(@RequestHeader("TOKEN") final String token,
		@NotNull @PathVariable final Long id) {
		tokenService.validTokenAuthority(token);
		return new Response<>(eventService.deleteEventOne(id), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public Response<?> deleteEventList(@RequestHeader("TOKEN") final String token,
		@NotNull @RequestBody final EventDeleteRequestDto dto) {
		tokenService.validTokenAuthority(token);
		return new Response<>(eventService.deleteEventList(dto.getId()), HttpStatus.OK);
	}

}
