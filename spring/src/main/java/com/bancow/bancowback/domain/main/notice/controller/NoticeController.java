package com.bancow.bancowback.domain.main.notice.controller;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.main.notice.dto.NoticeAddDto;
import com.bancow.bancowback.domain.main.notice.dto.NoticeDeleteListDto;
import com.bancow.bancowback.domain.main.notice.dto.NoticeUpdateDto;
import com.bancow.bancowback.domain.main.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

	private final NoticeService noticeService;

	@GetMapping("/public/{id}")
	public Response<?> getPublicNotice(@PathVariable Long id){
		return new Response<>(noticeService.getPublicNotice(id), HttpStatus.OK);
	}

	@GetMapping("/public")
	public Response<?> getPublicNoticePaging(@RequestParam int page){
		return new Response<>(noticeService.getPublicNoticePaging(page), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Response<?> getNotice(@RequestHeader("TOKEN") String token, @PathVariable Long id) {
		return new Response<>(noticeService.getNotice(token, id), HttpStatus.OK);
	}

	@GetMapping
	public Response<?> getNoticePaging(@RequestHeader("TOKEN") String token, @RequestParam int page) {
		return new Response<>(noticeService.getNoticePaging(token, page), HttpStatus.OK);
	}

	@PostMapping("/add")
	public Response<?> addNotice(@RequestHeader("TOKEN") String token, @RequestBody @Valid NoticeAddDto noticeAddDto) {
		return new Response<>(noticeService.addNotice(token, noticeAddDto), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public Response<?> deleteNotice(@RequestHeader("TOKEN") String token, @PathVariable Long id) {
		return new Response<>(noticeService.deleteNotice(token, id), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public Response<?> deleteNoticeList(@RequestHeader("TOKEN") String token, @RequestBody NoticeDeleteListDto noticeDeleteListDto){
		return new Response<>(noticeService.deleteNoticeList(token, noticeDeleteListDto.getIdList()), HttpStatus.OK);
	}

	@PatchMapping("/update")
	public Response<?> updateNotice(@RequestHeader("TOKEN") String token, @RequestBody NoticeUpdateDto noticeUpdateDto){

		return new Response<>(noticeService.updateNotice(token, noticeUpdateDto), HttpStatus.OK);
	}

}
