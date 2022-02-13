package com.bancow.bancowback.domain.sub.farm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FarmDeleteRequestDto {
	private List<Long> id;
}
