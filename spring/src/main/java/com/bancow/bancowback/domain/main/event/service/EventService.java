package com.bancow.bancowback.domain.main.event.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.ErrorCode;
import com.bancow.bancowback.domain.common.exception.EventException;

import com.bancow.bancowback.domain.main.event.dto.EventAddRequestDto;
import com.bancow.bancowback.domain.main.event.dto.EventDistributeResponseDto;
import com.bancow.bancowback.domain.main.event.dto.EventInfo;
import com.bancow.bancowback.domain.main.event.dto.EventUpdateRequestDto;
import com.bancow.bancowback.domain.main.event.entity.Event;
import com.bancow.bancowback.domain.main.event.mapper.EventMapper;
import com.bancow.bancowback.domain.main.event.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
	private final EventRepository eventRepository;
	private final EventMapper eventMapper;

	public ServiceResult addEvent(EventInfo eventInfo) {
		Event event = eventMapper.toEntity(eventInfo.getManager(), (EventAddRequestDto)eventInfo.getDto(),
			eventInfo.getImagePath());
		eventRepository.save(event);
		return ServiceResult.success("이벤트가 등록 됐습니다.");
	}

	public List<EventDistributeResponseDto> getEventDistribute() {
		List<Event> eventList = eventRepository.findByStatus(true);

		if (eventList.size() == 0) {
			throw new EventException(ErrorCode.NOT_FOUND_EVENT, "이벤트 없음");
		}

		return eventList.stream().map(event -> eventMapper.toDistributeResponseDto(event)).collect(Collectors.toList());
	}

	public Event getEventId(Long id) {
		return eventRepository.findById(id)
			.orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND_EVENT, "이벤트 없음"));
	}

	public ServiceResult editEventImage(EventInfo<EventUpdateRequestDto> eventInfo) {
		Event event = eventMapper.toUpdateEntity(getEventId(eventInfo.getDto().getId()), eventInfo.getDto(),
			eventInfo.getImagePath());
		eventRepository.save(event);
		return ServiceResult.success("이벤트가 업데이트 됐습니다.");
	}

	public ServiceResult editEventNotImage(EventUpdateRequestDto eventDto) {
		Event event = eventMapper.toUpdateNotImageEntity(getEventId(eventDto.getId()), eventDto);
		eventRepository.save(event);
		return ServiceResult.success("이벤트가 업데이트 됐습니다.");
	}

	public ServiceResult deleteEventOne(Long id) {
		eventRepository.delete(getEventId(id));
		return ServiceResult.success("이벤트가 삭제 됐습니다.");
	}

	public Object deleteEventList(List<Long> id) {
		List<Event> deleteEventList = eventRepository.findByIdIn(id);

		if (deleteEventList.size() == 0) {
			throw new EventException(ErrorCode.NOT_FOUND_EVENT, "해당 이벤트 없음");
		}

		deleteEventList
			.stream().forEach(e -> {
				eventRepository.delete(e);
			});

		return ServiceResult.success("이벤트 삭제 성공.");

	}
}
