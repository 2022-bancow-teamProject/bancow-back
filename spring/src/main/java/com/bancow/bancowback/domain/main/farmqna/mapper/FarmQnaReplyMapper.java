package com.bancow.bancowback.domain.main.farmqna.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.main.farmqna.dto.FarmQnaReplyDto;
import com.bancow.bancowback.domain.main.farmqna.entity.FarmQna;
import com.bancow.bancowback.domain.main.farmqna.entity.FarmQnaReply;
import com.bancow.bancowback.domain.manager.entity.Manager;

@Mapper(componentModel = "spring")
public interface FarmQnaReplyMapper {
	FarmQnaReplyMapper INSTANCE = Mappers.getMapper(FarmQnaReplyMapper.class);

	default public FarmQnaReply toEntity(FarmQnaReplyDto dto, FarmQna farmQna, Manager manager) {
		return FarmQnaReply.builder()
			.farmQna(farmQna)
			.manager(manager)
			.mailTitle(dto.getMailTitle())
			.answer(dto.getAnswer())
			.createDate(LocalDateTime.now())
			.build();
	}
}
