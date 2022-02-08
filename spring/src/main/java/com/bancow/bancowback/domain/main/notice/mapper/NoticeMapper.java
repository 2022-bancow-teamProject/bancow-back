package com.bancow.bancowback.domain.main.notice.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.main.notice.dto.NoticeRequestDto;
import com.bancow.bancowback.domain.main.notice.entity.Notice;

@Mapper(componentModel = "spring")
public interface NoticeMapper {

	NoticeMapper INSTANCE = Mappers.getMapper(NoticeMapper.class);

	default public Notice toEntity(NoticeRequestDto noticeInputDto){
		return Notice.builder()
			.noticeCategory(noticeInputDto.getNoticeCategory())
			.username(noticeInputDto.getUsername())
			.title(noticeInputDto.getTitle())
			.message(noticeInputDto.getMessage())
			.status(false)
			.createDate(LocalDateTime.now())
			.build();
	}
}
