package com.bancow.bancowback.domain.main.event.dto;

import com.bancow.bancowback.domain.manager.entity.Manager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventInfo<T> {
	private Manager manager;
	private T Dto;
	private String imagePath;
}
