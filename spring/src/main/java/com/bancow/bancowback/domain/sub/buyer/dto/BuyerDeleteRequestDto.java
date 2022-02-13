package com.bancow.bancowback.domain.sub.buyer.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyerDeleteRequestDto {
	private List<Long> id;
}
