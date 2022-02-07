package com.bancow.bancowback.domain.main.popup.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;

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
public class PopupResponseDto {

	private Long id;

	private String title;

	@JsonProperty("start_date")
	private LocalDate startDate;

	@JsonProperty("end_date")
	private LocalDate endDate;

	private String image;

	private Boolean status;

	@JsonProperty("create_date")
	private LocalDateTime createDate;

	@Column(name = "user_name")
	private String username;
}
