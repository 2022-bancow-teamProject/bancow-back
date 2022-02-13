package com.bancow.bancowback.domain.sub.buyer.dto;

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
public class BuyerPagingResponseDto {

	private Long id;

	@JsonProperty("buyer_name")
	private String buyerName;

	private String title;

	@JsonProperty("user_name")
	private String userName;

	@JsonProperty("farm_name")
	private String farmName;

	private Boolean status;

	@JsonProperty("create_date")
	private LocalDateTime create_date;
}
