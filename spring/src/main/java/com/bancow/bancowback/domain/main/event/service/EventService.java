package com.bancow.bancowback.domain.main.event.service;

import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.main.event.dto.EventAddRequestDto;
import com.bancow.bancowback.domain.main.event.dto.EventInfo;
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

}
