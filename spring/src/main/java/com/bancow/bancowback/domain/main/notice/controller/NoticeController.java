package com.bancow.bancowback.domain.main.notice.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.main.notice.dto.NoticeDeleteListDto;
import com.bancow.bancowback.domain.main.notice.dto.NoticeRequestDto;
import com.bancow.bancowback.domain.main.notice.entity.Notice;
import com.bancow.bancowback.domain.main.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

	private final NoticeService noticeService;

	@GetMapping("/{id}")
	public Response<Notice> getNotice(@RequestHeader("TOKEN") String token, @PathVariable Long id) {
		Notice notice = noticeService.getNotice(token, id);
		return new Response<>(notice, HttpStatus.OK);
	}

	@GetMapping
	public Response<Page<Notice>> getNoticePaging(@RequestHeader("TOKEN") String token, @RequestParam int page) {
		Page<Notice> pagingNotice = noticeService.getNoticePaging(page, token);
		return new Response<>(pagingNotice, HttpStatus.OK);
	}

	@PostMapping("/add")
	public Response<Notice> addNotice(@RequestHeader("TOKEN") String token, @RequestBody @Valid NoticeRequestDto noticeRequestDto) {
		Notice result = noticeService.addNotice(token, noticeRequestDto);
		return new Response<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public Response<?> deleteNotice(@RequestHeader("TOKEN") String token, @PathVariable Long id) {
		ServiceResult result = noticeService.deleteNotice(token, id);
		return new Response<>(result, HttpStatus.OK);
	}

	@PostMapping("/delete")
	public Response<?> deleteNoticeList(@RequestHeader("TOKEN") String token, @RequestBody NoticeDeleteListDto noticeDeleteList){
		ServiceResult result = noticeService.deleteNoticeList(token, noticeDeleteList);
		return new Response<>(result, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public Response<?> updateNotice(@RequestHeader("TOKEN") String token, @PathVariable Long id, @RequestBody NoticeRequestDto noticeInput){

		ServiceResult result = noticeService.updateNotice(token, id, noticeInput);
		return new Response<>(result, HttpStatus.OK);
	}

}
