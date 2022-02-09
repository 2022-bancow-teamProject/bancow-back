package com.bancow.bancowback.domain.main.farmqna.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.main.farmqna.dto.FarmQnaAddRequestDto;
import com.bancow.bancowback.domain.main.farmqna.entity.FarmQna;

@Mapper(componentModel = "spring")
public interface FarmQnaMapper {
	FarmQnaMapper INSTANCE = Mappers.getMapper(FarmQnaMapper.class);

	default public FarmQna toEntity(FarmQnaAddRequestDto dto) {
		return FarmQna.builder()
			.name(dto.getName())
			.phoneNumber(dto.getPhoneNumber())
			.email(dto.getEmail())
			.farmName(dto.getFarmName())
			.farmAddress(dto.getFarmAddress())
			.cowNum(dto.getCowNum())
			.feedName(dto.getFeedName())
			.checked(false)
			.createDate(LocalDateTime.now())
			.build();
	}
}
