package com.bancow.bancowback.domain.sub.buyer.mapper;

import org.springframework.stereotype.Component;

import com.bancow.bancowback.domain.sub.buyer.dto.BuyerDistributeResponseDto;
import com.bancow.bancowback.domain.sub.buyer.entity.Buyer;

@Component
public class BuyerMapper {
	public BuyerDistributeResponseDto toDistributeResponseDto(Buyer buyer){
		return BuyerDistributeResponseDto.builder()
			.id(buyer.getId())
			.buyerName(buyer.getBuyerName())
			.farmName(buyer.getFarm().getFarmName())
			.farmCEOName(buyer.getFarm().getCeoName())
			.farmImage(buyer.getFarm().getFarmImage())
			.build();
	}
}
