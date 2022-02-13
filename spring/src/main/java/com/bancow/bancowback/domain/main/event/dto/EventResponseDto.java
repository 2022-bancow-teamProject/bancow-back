package com.bancow.bancowback.domain.main.event.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponseDto {

	private Long id;

	private String title;

	private String content;

	private String url;

	private String image;

	private Boolean status;

	@JsonProperty("user_name")
	private String userName;

	@JsonProperty("start_date")
	private LocalDate startDate;

	@JsonProperty("end_date")
	private LocalDate endDate;

	@JsonProperty("create_date")
	private LocalDateTime createDate;
}
