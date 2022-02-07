package com.bancow.bancowback.domain.main.popup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.main.popup.dto.PopupAddRequestDto;
import com.bancow.bancowback.domain.main.popup.entity.Popup;
import com.bancow.bancowback.domain.manager.entity.Manager;

@Mapper(componentModel = "spring")
public interface PopupMapper {

	PopupMapper INSTANCE = Mappers.getMapper(PopupMapper.class);

	default public Popup toEntity(Manager manager,PopupAddRequestDto addDto, String imagePath) {
		return Popup.builder()
			.title(addDto.getTitle())
			.startDate(addDto.getStartDate())
			.endDate(addDto.getEndDate())
			.image(imagePath)
			.manager(manager)
			.status(Boolean.TRUE)
			.build();
	}
}