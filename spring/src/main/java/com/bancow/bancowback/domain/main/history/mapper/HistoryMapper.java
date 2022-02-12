package com.bancow.bancowback.domain.main.history.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.main.history.dto.HistoryAddDto;
import com.bancow.bancowback.domain.main.history.dto.HistoryResponseDto;
import com.bancow.bancowback.domain.main.history.dto.HistoryUpdateDto;
import com.bancow.bancowback.domain.main.history.entity.History;
import com.bancow.bancowback.domain.manager.entity.Manager;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

	HistoryMapper INSTANCE = Mappers.getMapper(
		HistoryMapper.class);

	default public History toEntity(Manager manager, HistoryAddDto historyAddDto) {
		return History.builder()
			.title(historyAddDto.getTitle())
			.message(historyAddDto.getMessage())
			.status(historyAddDto.isStatus())
			.createDate(LocalDateTime.now())
			.updateDate(LocalDateTime.now())
			.manager(manager)
			.build();
	}

	default public History toAdd(Manager manger, HistoryAddDto historyAddDto) {
		return History.builder()
			.title(historyAddDto.getTitle())
			.message(historyAddDto.getMessage())
			.status(historyAddDto.isStatus())
			.createDate(LocalDateTime.now())
			.updateDate(LocalDateTime.now())
			.manager(manger)
			.build();
	}

	default public History toUpdate(Manager manager, History history, HistoryUpdateDto historyUpdateDto) {
		return History.builder()
			.id(historyUpdateDto.getId())
			.title(historyUpdateDto.getTitle())
			.message(historyUpdateDto.getMessage())
			.status(historyUpdateDto.isStatus())
			.createDate(history.getCreateDate())
			.updateDate(LocalDateTime.now())
			.manager(manager)
			.build();
	}

	default public HistoryResponseDto toResponseDto(History history) {
		return HistoryResponseDto.builder()
			.id(history.getId())
			.title(history.getTitle())
			.message(history.getMessage())
			.status(history.isStatus())
			.createDate(LocalDateTime.now())
			.updateDate(LocalDateTime.now())
			.username(history.getManager().getUsername())
			.build();
	}
}
