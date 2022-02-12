package com.bancow.bancowback.domain.main.notice.mapper;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.main.notice.dto.NoticeAddDto;
import com.bancow.bancowback.domain.main.notice.dto.NoticeResponseDto;
import com.bancow.bancowback.domain.main.notice.dto.NoticeUpdateDto;
import com.bancow.bancowback.domain.main.notice.entity.Notice;
import com.bancow.bancowback.domain.main.notice.entity.NoticeCategory;
import com.bancow.bancowback.domain.manager.entity.Manager;

@Mapper(componentModel = "spring")
public interface NoticeMapper {

	NoticeMapper INSTANCE = Mappers.getMapper(NoticeMapper.class);

	default public Notice toEntity(Manager manager, NoticeAddDto noticeAddDto) {
		return Notice.builder()
			.noticeCategory(noticeAddDto.getNoticeCategory())
			.title(noticeAddDto.getTitle())
			.message(noticeAddDto.getMessage())
			.status(noticeAddDto.isStatus())
			.createDate(LocalDateTime.now())
			.updateDate(LocalDateTime.now())
			.manager(manager)
			.build();
	}

	default public Notice toAdd(Manager manger, NoticeAddDto noticeAddDto) {
		return Notice.builder()
			.noticeCategory(noticeAddDto.getNoticeCategory())
			.title(noticeAddDto.getTitle())
			.message(noticeAddDto.getMessage())
			.status(noticeAddDto.isStatus())
			.createDate(LocalDateTime.now())
			.updateDate(LocalDateTime.now())
			.manager(manger)
			.build();
	}

	default public Notice toUpdate(Manager manager, Notice notice, NoticeUpdateDto noticeUpdateDto) {
		return Notice.builder()
			.id(noticeUpdateDto.getId())
			.noticeCategory(noticeUpdateDto.getNoticeCategory())
			.title(noticeUpdateDto.getTitle())
			.message(noticeUpdateDto.getMessage())
			.status(noticeUpdateDto.isStatus())
			.createDate(notice.getCreateDate())
			.updateDate(LocalDateTime.now())
			.manager(manager)
			.build();
	}

	default public NoticeResponseDto toResponseDto(Notice notice) {
		return NoticeResponseDto.builder()
			.id(notice.getId())
			.noticeCategory(notice.getNoticeCategory())
			.title(notice.getTitle())
			.message(notice.getMessage())
			.status(notice.isStatus())
			.createDate(LocalDateTime.now())
			.updateDate(LocalDateTime.now())
			.username(notice.getManager().getUsername())
			.build();
	}
}
