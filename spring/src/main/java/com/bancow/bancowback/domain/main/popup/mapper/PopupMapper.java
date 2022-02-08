package com.bancow.bancowback.domain.main.popup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.main.popup.dto.PopupAddRequestDto;
import com.bancow.bancowback.domain.main.popup.dto.PopupResponseDto;
import com.bancow.bancowback.domain.main.popup.dto.PopupUpdateRequestDto;
import com.bancow.bancowback.domain.main.popup.entity.Popup;
import com.bancow.bancowback.domain.manager.entity.Manager;

@Mapper(componentModel = "spring")
public interface PopupMapper {

	PopupMapper INSTANCE = Mappers.getMapper(PopupMapper.class);

	default public Popup toEntity(Manager manager, PopupAddRequestDto addDto, String imagePath) {
		return Popup.builder()
			.title(addDto.getTitle())
			.startDate(addDto.getStartDate())
			.endDate(addDto.getEndDate())
			.image(imagePath)
			.manager(manager)
			.status(Boolean.TRUE)
			.build();
	}

	default public PopupResponseDto toResponseDto(Popup popup) {
		return PopupResponseDto.builder()
			.id(popup.getId())
			.title(popup.getTitle())
			.startDate(popup.getStartDate())
			.endDate(popup.getEndDate())
			.image("https://kr.object.ncloudstorage.com/bancowback/"+popup.getImage())
			.status(popup.getStatus())
			.createDate(popup.getCreateDate())
			.username(popup.getManager().getUsername())
			.build();
	}

	default public Popup toUpdateEntity(Popup popup, PopupUpdateRequestDto popupUpdateRequestDto , String popupUploadPath){
		return Popup.builder()
			.id(popup.getId())
			.title(popupUpdateRequestDto.getTitle())
			.startDate(popupUpdateRequestDto.getStartDate())
			.endDate(popupUpdateRequestDto.getEndDate())
			.image(popupUploadPath)
			.manager(popup.getManager())
			.status(popupUpdateRequestDto.getStatus())
			.createDate(popup.getCreateDate())
			.build();
	}

	default public Popup toUpdateNotImageEntity(Popup popup, PopupUpdateRequestDto popupUpdateRequestDto){
		return Popup.builder()
			.id(popup.getId())
			.title(popupUpdateRequestDto.getTitle())
			.startDate(popupUpdateRequestDto.getStartDate())
			.endDate(popupUpdateRequestDto.getEndDate())
			.image(popup.getImage())
			.manager(popup.getManager())
			.status(popupUpdateRequestDto.getStatus())
			.createDate(popup.getCreateDate())
			.build();
	}
}