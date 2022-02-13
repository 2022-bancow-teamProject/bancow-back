package com.bancow.bancowback.domain.sub.news.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsDeleteRequestDto {
	private List<Long> id;
}
