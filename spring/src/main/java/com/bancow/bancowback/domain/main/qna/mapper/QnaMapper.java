package com.bancow.bancowback.domain.main.qna.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.main.qna.dto.QnaRequestDto;
import com.bancow.bancowback.domain.main.qna.entity.Qna;

@Mapper(componentModel = "spring")
public interface QnaMapper {

	QnaMapper INSTANCE = Mappers.getMapper(QnaMapper.class);

	default public Qna toEntity(QnaRequestDto qnaInputDto) {
		return Qna.builder()
			.category(qnaInputDto.getCategory())
			.phoneNumber(qnaInputDto.getPhoneNumber())
			.email(qnaInputDto.getEmail())
			.title(qnaInputDto.getTitle())
			.message(qnaInputDto.getMessage())
			.checked(false)
			.createDate(LocalDateTime.now())
			.build();
	}
}
