package com.bancow.bancowback.domain.main.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.main.event.dto.EventAddRequestDto;
import com.bancow.bancowback.domain.main.event.dto.EventDistributeResponseDto;
import com.bancow.bancowback.domain.main.event.entity.Event;
import com.bancow.bancowback.domain.manager.entity.Manager;

@Mapper(componentModel = "spring")
public interface EventMapper {

	EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

	default public Event toEntity(Manager manager, EventAddRequestDto addDto, String imagePath) {
		return Event.builder()
			.title(addDto.getTitle())
			.startDate(addDto.getStartDate())
			.endDate(addDto.getEndDate())
			.image(imagePath)
			.manager(manager)
			.status(Boolean.FALSE)
			.build();
	}

	default public EventDistributeResponseDto toDistributeResponseDto(Event event) {
		return EventDistributeResponseDto.builder()
			.id(event.getId())
			.startDate(event.getStartDate())
			.endDate(event.getEndDate())
			.image(event.getImage())
			.build();
	}

}
