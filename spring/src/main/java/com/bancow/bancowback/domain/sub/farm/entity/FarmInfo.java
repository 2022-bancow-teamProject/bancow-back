package com.bancow.bancowback.domain.sub.farm.entity;

import com.bancow.bancowback.domain.manager.entity.Manager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmInfo<T> {
	private Manager manager;
	private T Dto;
	private String imagePath;

}