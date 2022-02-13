package com.bancow.bancowback.domain.sub.news.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.manager.entity.Manager;
import com.bancow.bancowback.domain.sub.news.dto.NewsRequestDto;
import com.bancow.bancowback.domain.sub.news.dto.NewsResponseDto;
import com.bancow.bancowback.domain.sub.news.dto.NewsUpdateRequestDto;
import com.bancow.bancowback.domain.sub.news.entity.News;

@Mapper(componentModel = "spring")
public interface NewsMapper {
	NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

	default public News toEntity(NewsRequestDto dto, Manager manager) {
		return News.builder()
			.title(dto.getTitle())
			.media(dto.getMedia())
			.url(dto.getUrl())
			.manager(manager)
			.createDate(LocalDateTime.now())
			.build();
	}

	default public NewsResponseDto toResponseDto(News news) {
		return NewsResponseDto.builder()
			.id(news.getId())
			.title(news.getTitle())
			.url(news.getUrl())
			.media(news.getMedia())
			.managerName(news.getManager().getUsername())
			.createDate(news.getCreateDate())
			.build();
	}

	default public News toUpdateNewsEntity(News news, NewsUpdateRequestDto dto) {
		return News.builder()
			.id(news.getId())
			.title(dto.getTitle())
			.url(dto.getUrl())
			.media(dto.getMedia())
			.manager(news.getManager())
			.createDate(news.getCreateDate())
			.build();
	}
}
