package com.bancow.bancowback.domain.sub.news.controller;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.sub.news.dto.NewsDeleteRequestDto;
import com.bancow.bancowback.domain.sub.news.dto.NewsRequestDto;
import com.bancow.bancowback.domain.sub.news.dto.NewsResponseDto;
import com.bancow.bancowback.domain.sub.news.dto.NewsUpdateRequestDto;
import com.bancow.bancowback.domain.sub.news.entity.News;
import com.bancow.bancowback.domain.sub.news.service.NewsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {

	private final NewsService newsService;

	@PostMapping("/add")
	public Response<ServiceResult> addNews(@RequestHeader("TOKEN") String token,
		@RequestBody @Valid NewsRequestDto newsRequestDto) {
		ServiceResult result = newsService.addNews(token, newsRequestDto);
		return new Response<>(result, HttpStatus.OK);
	}

	@GetMapping
	public Response<Page<NewsResponseDto>> getNewsPaging(@RequestParam int page) {
		Page<NewsResponseDto> pagingNews = newsService.getNewsPaging(page);
		return new Response<>(pagingNews, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Response<NewsResponseDto> getNews(@RequestHeader("TOKEN") String token,
		@PathVariable Long id) {
		NewsResponseDto result = newsService.getNews(token, id);
		return new Response<>(result, HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	public Response<ServiceResult> updateNews(@RequestHeader("TOKEN") String token,
		@PathVariable Long id, @RequestBody NewsUpdateRequestDto dto) {
		ServiceResult result = newsService.updateNews(token, id, dto);
		return new Response<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public Response<ServiceResult> deleteNews(@RequestHeader("TOKEN") String token,
		@PathVariable Long id) {
		ServiceResult result = newsService.deleteNews(token, id);
		return new Response<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public Response<ServiceResult> deleteNewsList(@RequestHeader("TOKEN") String token,
		@RequestBody @Valid NewsDeleteRequestDto dto) {
		ServiceResult result = newsService.deleteNewsList(token, dto);
		return new Response<>(result, HttpStatus.OK);
	}
}
