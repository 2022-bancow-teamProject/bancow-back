package com.bancow.bancowback.domain.sub.news.dto;

import java.time.LocalDateTime;

import com.bancow.bancowback.domain.manager.entity.Manager;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class NewsResponseDto {

	private Long id;

	private String title;

	private String media;

	private String url;

	@JsonProperty("manager_name")
	private String managerName;

	@JsonProperty("create_date")
	private LocalDateTime createDate;
}
