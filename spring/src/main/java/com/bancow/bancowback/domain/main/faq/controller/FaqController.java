package com.bancow.bancowback.domain.main.faq.controller;

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
import com.bancow.bancowback.domain.main.faq.dto.FaqAddDto;
import com.bancow.bancowback.domain.main.faq.dto.FaqDeleteListDto;
import com.bancow.bancowback.domain.main.faq.dto.FaqUpdateDto;
import com.bancow.bancowback.domain.main.faq.service.FaqService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/faq")
public class FaqController {

	private final FaqService faqService;

	@GetMapping("/public/{id}")
	public Response<?> getPublicFaq(@PathVariable Long id){
		return new Response<>(faqService.getPublicFaq(id), HttpStatus.OK);
	}

	@GetMapping("/public")
	public Response<?> getPublicFaqPaging(@RequestParam int page){
		return new Response<>(faqService.getPublicFaqPaging(page), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Response<?> getFaq(@RequestHeader("TOKEN") String token, @PathVariable Long id) {
		return new Response<>(faqService.getFaq(token, id), HttpStatus.OK);
	}

	@GetMapping
	public Response<?> getFaqPaging(@RequestHeader("TOKEN") String token, @RequestParam int page) {
		return new Response<>(faqService.getFaqPaging(token, page), HttpStatus.OK);
	}

	@PostMapping("/add")
	public Response<?> addFaq(@RequestHeader("TOKEN") String token, @RequestBody @Valid FaqAddDto faqAddDto) {
		return new Response<>(faqService.addFaq(token, faqAddDto), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public Response<?> deleteFaq(@RequestHeader("TOKEN") String token, @PathVariable Long id) {
		return new Response<>(faqService.deleteFaq(token, id), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public Response<?> deleteFaqList(@RequestHeader("TOKEN") String token, @RequestBody FaqDeleteListDto faqDeleteListDto){
		return new Response<>(faqService.deleteFaqList(token, faqDeleteListDto.getIdList()), HttpStatus.OK);
	}

	@PatchMapping("/update")
	public Response<?> updateFaq(@RequestHeader("TOKEN") String token, @RequestBody FaqUpdateDto faqUpdateDto){

		return new Response<>(faqService.updateFaq(token, faqUpdateDto), HttpStatus.OK);
	}

}
