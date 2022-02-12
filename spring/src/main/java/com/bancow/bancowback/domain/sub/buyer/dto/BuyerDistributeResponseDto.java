package com.bancow.bancowback.domain.sub.buyer.dto;

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
public class BuyerDistributeResponseDto {

	private Long id;

	@JsonProperty("buyer_name")
	private String buyerName;

	@JsonProperty("farm_name")
	private String farmName;

	@JsonProperty("farm_ceo_name")
	private String farmCEOName;

	@JsonProperty("farm_image")
	private String farmImage;

}
