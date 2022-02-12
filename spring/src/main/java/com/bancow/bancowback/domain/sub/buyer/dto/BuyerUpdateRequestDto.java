package com.bancow.bancowback.domain.sub.buyer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyerUpdateRequestDto {

	private Long id;

	private Boolean status;

}