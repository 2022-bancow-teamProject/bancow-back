package com.bancow.bancowback.domain.sub.chart.dto;

import java.util.List;
import java.util.Map;

import com.bancow.bancowback.domain.sub.chart.entity.KoreanCowCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KoreanCowResponseDto {
	private KoreanCowCategory category;
	private List<Map<Integer, Long>> info;
}
