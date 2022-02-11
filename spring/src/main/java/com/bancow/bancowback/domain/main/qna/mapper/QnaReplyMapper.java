package com.bancow.bancowback.domain.main.qna.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.main.qna.dto.QnaReplyDto;
import com.bancow.bancowback.domain.main.qna.entity.Qna;
import com.bancow.bancowback.domain.main.qna.entity.QnaReply;
import com.bancow.bancowback.domain.manager.entity.Manager;

@Mapper(componentModel = "spring")
public interface QnaReplyMapper {
	QnaReplyMapper INSTANCE = Mappers.getMapper(QnaReplyMapper.class);

	default public QnaReply toEntity(QnaReplyDto dto, Qna qna, Manager manager) {
		return QnaReply.builder()
			.qna(qna)
			.manager(manager)
			.mailTitle(dto.getMailTitle())
			.answer(dto.getAnswer())
			.createDate(LocalDateTime.now())
			.build();
	}
}
