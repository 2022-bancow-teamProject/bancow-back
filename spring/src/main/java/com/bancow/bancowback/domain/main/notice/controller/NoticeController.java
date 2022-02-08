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
	public Response<Notice> getNotice(@PathVariable Long id, @RequestHeader("TOKEN") String token) {
		Notice notice = noticeService.getNotice(token, id);
		return new Response<>(notice, HttpStatus.OK);
	}

	@GetMapping
	public Response<Page<Notice>> getNoticePaging(@RequestParam int page, @RequestHeader("TOKEN") String token){
		Page<Notice> pagingNotice = noticeService.getNoticePaging(page, token);
		return new Response<>(pagingNotice, HttpStatus.OK);
	}

	@PostMapping("/add")
	public Response<Notice> addNotice(@RequestBody @Valid NoticeRequestDto noticeRequestDto) {
		Notice result = noticeService.addNotice(noticeRequestDto);
		return new Response<>(result, HttpStatus.OK);
	}


	@DeleteMapping("/delete/{id}")
	public Response<?> deleteNotice(@PathVariable Long id) {

		ServiceResult result = noticeService.deleteNotice(id);
		if(!result.isResult()){
			return new Response<>(result, HttpStatus.BAD_REQUEST);
		}
		return new Response<>(result, HttpStatus.OK);
	}


	@PostMapping("/delete")
	public Response<?> deleteNoticeList(@RequestBody NoticeDeleteListDto noticeDeleteList){

		ServiceResult result = noticeService.deleteNoticeList(noticeDeleteList);
		if(!result.isResult()){
			return new Response<>(result, HttpStatus.BAD_REQUEST);
		}
		return new Response<>(result, HttpStatus.OK);
	}


	@PutMapping("/update/{id}")
	public Response<?> updateNotice(@PathVariable Long id, @RequestBody NoticeRequestDto noticeInput){

		ServiceResult result = noticeService.updateNotice(id, noticeInput);
		if(!result.isResult()){
			return new Response<>(result, HttpStatus.BAD_REQUEST);
		}
		return new Response<>(result, HttpStatus.OK);
	}

}
