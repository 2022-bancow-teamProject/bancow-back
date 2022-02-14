package com.bancow.bancowback.domain.main.qna.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.main.qna.dto.QnaRequestDto;
import com.bancow.bancowback.domain.main.qna.dto.QnaResponseDto;
import com.bancow.bancowback.domain.main.qna.entity.Qna;

@Mapper(componentModel = "spring")
public interface QnaMapper {

	QnaMapper INSTANCE = Mappers.getMapper(QnaMapper.class);

	default public Qna toEntity(QnaRequestDto qnaInputDto) {
		return Qna.builder()
			.category(qnaInputDto.getCategory())
			.qnaName(qnaInputDto.getQnaName())
			.phoneNumber(qnaInputDto.getPhoneNumber())
			.email(qnaInputDto.getEmail())
			.title(qnaInputDto.getTitle())
			.message(qnaInputDto.getMessage())
			.checked(false)
			.createDate(LocalDateTime.now())
			.build();
	}

	default QnaResponseDto toResponse(Qna qna) {
		return QnaResponseDto.builder()
			.id(qna.getId())
			.category(qna.getCategory())
			.qnaName(qna.getQnaName())
			.phoneNumber(qna.getPhoneNumber())
			.email(qna.getEmail())
			.title(qna.getTitle())
			.message(qna.getMessage())
			.checked(qna.isChecked())
			.createDate(qna.getCreateDate())
			.build();
	}
}
