package com.bancow.bancowback.domain.sub.farm.dto;

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
public class FarmDistributeResponseDto {

	private Long id;

	private String title;

	@JsonProperty("farm_name")
	private String farmName;

	@JsonProperty("ceo_name")
	private String ceoName;

	@JsonProperty("farm_ceo_image")
	private String farmCEOImage;
}
