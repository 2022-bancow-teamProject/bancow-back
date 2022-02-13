package com.bancow.bancowback.domain.sub.farm.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmUpdateRequestDto {

	@NotBlank
	private Long id;

	@JsonProperty("farm_name")
	private String farmName;

	@JsonProperty("ceo_name")
	private String ceoName;

	@NotBlank
	private String title;

	@NotBlank
	private String content;

	private String farmImageUploadPath;

	private String farmCEOImageUploadPath;

	@NotBlank
	private Boolean status;

}
