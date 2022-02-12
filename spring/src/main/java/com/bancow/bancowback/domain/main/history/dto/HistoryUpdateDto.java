package com.bancow.bancowback.domain.main.history.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class HistoryUpdateDto {

	@NotNull
	private Long id;

	@NotEmpty
	private String title;

	@NotEmpty
	private String message;

	@NotNull
	private boolean status;
}