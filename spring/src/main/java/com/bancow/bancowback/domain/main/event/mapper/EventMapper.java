package com.bancow.bancowback.domain.main.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.main.event.dto.EventAddRequestDto;
import com.bancow.bancowback.domain.main.event.dto.EventDistributeResponseDto;
import com.bancow.bancowback.domain.main.event.dto.EventResponseDto;
import com.bancow.bancowback.domain.main.event.dto.EventUpdateRequestDto;
import com.bancow.bancowback.domain.main.event.entity.Event;
import com.bancow.bancowback.domain.manager.entity.Manager;

@Mapper(componentModel = "spring")
public interface EventMapper {

	EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

	default public Event toEntity(Manager manager, EventAddRequestDto addDto, String imagePath) {
		return Event.builder()
			.title(addDto.getTitle())
			.content(addDto.getContent())
			.url(addDto.getUrl())
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
			.title(event.getTitle())
			.content(event.getContent())
			.url(event.getUrl())
			.status(event.getStatus())
			.startDate(event.getStartDate())
			.endDate(event.getEndDate())
			.image("https://kr.object.ncloudstorage.com/bancowback/" + event.getImage())
			.build();
	}

	default public EventResponseDto toResponseDto(Event event) {
		return EventResponseDto.builder()
			.id(event.getId())
			.title(event.getTitle())
			.content(event.getContent())
			.url(event.getUrl())
			.status(event.getStatus())
			.userName(event.getManager().getUsername())
			.startDate(event.getStartDate())
			.endDate(event.getEndDate())
			.image("https://kr.object.ncloudstorage.com/bancowback/" + event.getImage())
			.createDate(event.getCreateDate())
			.build();
	}

	default public Event toUpdateEntity(Manager manager, Event event, EventUpdateRequestDto eventUpdateRequestDto,
		String eventUploadPath) {
		return Event.builder()
			.id(event.getId())
			.title(eventUpdateRequestDto.getTitle())
			.content(eventUpdateRequestDto.getContent())
			.url(eventUpdateRequestDto.getUrl())
			.startDate(eventUpdateRequestDto.getStartDate())
			.endDate(eventUpdateRequestDto.getEndDate())
			.image(eventUploadPath)
			.manager(manager)
			.status(eventUpdateRequestDto.getStatus())
			.createDate(event.getCreateDate())
			.build();
	}

	default public Event toUpdateNotImageEntity(Manager manager, Event event,
		EventUpdateRequestDto eventUpdateRequestDto) {
		return Event.builder()
			.id(event.getId())
			.title(eventUpdateRequestDto.getTitle())
			.content(eventUpdateRequestDto.getContent())
			.url(eventUpdateRequestDto.getUrl())
			.startDate(eventUpdateRequestDto.getStartDate())
			.endDate(eventUpdateRequestDto.getEndDate())
			.image(event.getImage())
			.manager(manager)
			.status(eventUpdateRequestDto.getStatus())
			.createDate(event.getCreateDate())
			.build();
	}
}
