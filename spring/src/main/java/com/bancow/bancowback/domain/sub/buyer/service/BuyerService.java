package com.bancow.bancowback.domain.sub.buyer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.exception.BuyerException;
import com.bancow.bancowback.domain.common.exception.ErrorCode;
import com.bancow.bancowback.domain.sub.buyer.dto.BuyerDistributeResponseDto;
import com.bancow.bancowback.domain.sub.buyer.entity.Buyer;
import com.bancow.bancowback.domain.sub.buyer.mapper.BuyerMapper;
import com.bancow.bancowback.domain.sub.buyer.repository.BuyerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuyerService {

	private final BuyerMapper buyermapper;
	private final BuyerRepository buyerRepository;

	public List<BuyerDistributeResponseDto> getBuyerDistribute() {
		List<Buyer> buyerList = buyerRepository.findByStatus(true);

		if(buyerList.size() == 0){
			throw new BuyerException(ErrorCode.NOT_FOUND_BUYER, "구매자 없음 없음");
		}

		return buyerList.stream().map(buyer -> buyermapper.toDistributeResponseDto(buyer)).collect(Collectors.toList());
	}
}
