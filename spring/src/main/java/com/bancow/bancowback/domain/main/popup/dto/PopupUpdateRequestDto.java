package com.bancow.bancowback.domain.main.popup.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopupUpdateRequestDto {

	@NotNull
	private Long id;

	@NotBlank
	private String title;

	@NotNull
	@JsonProperty("start_date")
	private LocalDate startDate;

	@NotNull
	@JsonProperty("end_date")
	private LocalDate endDate;

	@NotNull
	private Boolean status;
}

