package com.bancow.bancowback.domain.sub.news.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.ErrorCode;
import com.bancow.bancowback.domain.common.exception.NewsException;
import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.sub.news.dto.NewsDeleteRequestDto;
import com.bancow.bancowback.domain.sub.news.dto.NewsRequestDto;
import com.bancow.bancowback.domain.sub.news.dto.NewsResponseDto;
import com.bancow.bancowback.domain.sub.news.dto.NewsUpdateRequestDto;
import com.bancow.bancowback.domain.sub.news.entity.News;
import com.bancow.bancowback.domain.sub.news.mapper.NewsMapper;
import com.bancow.bancowback.domain.sub.news.repository.NewsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsService {

	private final NewsRepository newsRepository;
	private final NewsMapper newsMapper;
	private final TokenService tokenService;

	public ServiceResult addNews(String token, NewsRequestDto newsRequestDto) {
		tokenService.validTokenAuthority(token);
		News news = newsMapper.toEntity(newsRequestDto, tokenService.getManager(token));
		newsRepository.save(news);
		return ServiceResult.success("뉴스를 성공적으로 등록하였습니다.");
	}

	public Page<NewsResponseDto> getNewsPaging(int page) {
		Page<News> newsList = newsRepository.findAll(PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id")));
		return newsList.map(news -> newsMapper.toResponseDto(news));
	}

	public NewsResponseDto getNews(String token, Long id) {
		tokenService.validTokenAuthority(token);
		News news = newsRepository.findById(id)
			.orElseThrow(() -> new NewsException(ErrorCode.NOT_FOUND_NEWS, "해당 뉴스 없음"));
		return newsMapper.toResponseDto(news);
	}

	public ServiceResult updateNews(String token, Long id, NewsUpdateRequestDto newsUpdateRequestDto) {
		News news = newsRepository.findById(id)
			.orElseThrow(() -> new NewsException(ErrorCode.NOT_FOUND_NEWS, "해당 뉴스 없음"));

		News newsUpdate = newsMapper.toUpdateNewsEntity(news, newsUpdateRequestDto);
		newsRepository.save(newsUpdate);
		return ServiceResult.success("뉴스 수정을 완료하였습니다.");
	}

	public ServiceResult deleteNews(String token, Long id) {
		tokenService.validTokenAuthority(token);
		newsRepository.deleteById(id);
		return ServiceResult.success(id + "번 뉴스를 삭제하였습니다.");
	}

	public ServiceResult deleteNewsList(String token, NewsDeleteRequestDto dto) {
		tokenService.validTokenAuthority(token);
		List<News> deleteNewsList = newsRepository.findByIdIn(dto.getId());
		if (deleteNewsList.size() == 0) {
			throw new NewsException(ErrorCode.NOT_FOUND_NEWS, "해당 뉴스 없음");
		}
		deleteNewsList.forEach(newsRepository::delete);
		return ServiceResult.success("선택하신 뉴스를 삭제하였습니다.");
	}
}
