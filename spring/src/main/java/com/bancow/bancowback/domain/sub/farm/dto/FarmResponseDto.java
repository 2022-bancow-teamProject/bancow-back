package com.bancow.bancowback.domain.sub.farm.dto;

import java.time.LocalDateTime;

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
public class FarmResponseDto {

	private Long id;

	private String title;

	@JsonProperty("farm_name")
	private String farmName;

	@JsonProperty("ceo_name")
	private String ceoName;

	@JsonProperty("user_name")
	private String userName;

	private Boolean status;

	@JsonProperty("create_date")
	private LocalDateTime create_date;
}
