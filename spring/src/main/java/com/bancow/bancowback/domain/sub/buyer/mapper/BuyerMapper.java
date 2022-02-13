package com.bancow.bancowback.domain.sub.buyer.mapper;

import org.springframework.stereotype.Component;

import com.bancow.bancowback.domain.sub.buyer.dto.BuyerDetailResponseDto;
import com.bancow.bancowback.domain.sub.buyer.dto.BuyerDistributeResponseDto;
import com.bancow.bancowback.domain.sub.buyer.dto.BuyerPagingResponseDto;
import com.bancow.bancowback.domain.sub.buyer.dto.BuyerUpdateRequestDto;
import com.bancow.bancowback.domain.sub.buyer.entity.Buyer;

@Component
public class BuyerMapper {
	public BuyerDistributeResponseDto toDistributeResponseDto(Buyer buyer) {
		return BuyerDistributeResponseDto.builder()
			.id(buyer.getId())
			.buyerName(buyer.getBuyerName())
			.farmName(buyer.getFarm().getFarmName())
			.content(buyer.getContent())
			.title(buyer.getTitle())
			.farmCEOName(buyer.getFarm().getCeoName())
			.farmImage("https://kr.object.ncloudstorage.com/bancowback/" + buyer.getFarm().getFarmImage())
			.build();
	}

	public Buyer toUpdateNotImageEntity(Buyer buyer, BuyerUpdateRequestDto buyerUpdateRequestDto) {
		return Buyer.builder()
			.id(buyerUpdateRequestDto.getId())
			.buyerName(buyer.getBuyerName())
			.title(buyer.getTitle())
			.content(buyer.getContent())
			.manager(buyer.getManager())
			.farm(buyer.getFarm())
			.status(buyerUpdateRequestDto.getStatus())
			.createDate(buyer.getCreateDate())
			.build();
	}

	public BuyerPagingResponseDto toBuyerPagingResponseDto(Buyer buyer){
		return BuyerPagingResponseDto.builder()
			.id(buyer.getId())
			.buyerName(buyer.getBuyerName())
			.title(buyer.getTitle())
			.userName(buyer.getManager().getUsername())
			.farmName(buyer.getFarm().getFarmName())
			.status(buyer.getStatus())
			.create_date(buyer.getCreateDate())
			.build();
	}

	public BuyerDetailResponseDto toBuyerDetailResponseDto(Buyer buyer){
		return BuyerDetailResponseDto.builder()
			.id(buyer.getId())
			.buyerName(buyer.getBuyerName())
			.title(buyer.getTitle())
			.content(buyer.getContent())
			.userName(buyer.getManager().getUsername())
			.farmName(buyer.getFarm().getFarmName())
			.status(buyer.getStatus())
			.create_date(buyer.getCreateDate())
			.build();
	}
}
