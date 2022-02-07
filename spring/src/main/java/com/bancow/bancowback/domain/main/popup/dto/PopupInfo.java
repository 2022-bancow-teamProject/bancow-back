package com.bancow.bancowback.domain.main.popup.dto;

import com.bancow.bancowback.domain.manager.entity.Manager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopupInfo<T> {
	private Manager manager;
	private T Dto;
	private String imagePath;

}
