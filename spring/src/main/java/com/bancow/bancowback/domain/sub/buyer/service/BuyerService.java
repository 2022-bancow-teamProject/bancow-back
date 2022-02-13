package com.bancow.bancowback.domain.sub.buyer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.BuyerException;
import com.bancow.bancowback.domain.common.exception.ErrorCode;
import com.bancow.bancowback.domain.common.exception.EventException;
import com.bancow.bancowback.domain.sub.buyer.dto.BuyerDetailResponseDto;
import com.bancow.bancowback.domain.sub.buyer.dto.BuyerDistributeResponseDto;
import com.bancow.bancowback.domain.sub.buyer.dto.BuyerPagingResponseDto;
import com.bancow.bancowback.domain.sub.buyer.dto.BuyerUpdateRequestDto;
import com.bancow.bancowback.domain.sub.buyer.entity.Buyer;
import com.bancow.bancowback.domain.sub.buyer.mapper.BuyerMapper;
import com.bancow.bancowback.domain.sub.buyer.repository.BuyerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuyerService {

	private final BuyerMapper buyerMapper;
	private final BuyerRepository buyerRepository;

	public List<BuyerDistributeResponseDto> getBuyerDistribute() {
		List<Buyer> buyerList = buyerRepository.findByStatus(true);

		if (buyerList.size() == 0) {
			throw new BuyerException(ErrorCode.NOT_FOUND_BUYER, "구매자 리뷰 없음");
		}

		return buyerList.stream()
						.map(buyer -> buyerMapper.toDistributeResponseDto(buyer))
						.collect(Collectors.toList());
	}

	public ServiceResult editBuyer(BuyerUpdateRequestDto buyerUpdateRequestDto) {
		Buyer buyer = buyerMapper.toUpdateNotImageEntity(getBuyerId(buyerUpdateRequestDto.getId()),
			buyerUpdateRequestDto);
		buyerRepository.save(buyer);
		return ServiceResult.success("구매자리뷰 상태 값이 업데이트 됐습니다.");
	}

	private Buyer getBuyerId(Long id) {
		return buyerRepository.findById(id)
			.orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND_BUYER, "구매자 없음"));
	}

	public ServiceResult deleteBuyerOne(Long id) {
		buyerRepository.delete(getBuyerId(id));
		return ServiceResult.success("구매자리뷰가 삭제 됐습니다. ");
	}

	public ServiceResult deleteBuyerList(List<Long> id) {
		List<Buyer> deleteBuyerList = buyerRepository.findByIdIn(id);

		if (deleteBuyerList.size() == 0) {
			throw new BuyerException(ErrorCode.NOT_FOUND_BUYER, "구매자 없음");
		}

		deleteBuyerList
			.stream().forEach(e -> {
				buyerRepository.delete(e);
			});

		return ServiceResult.success("구매자 성공.");
	}

	public Page<BuyerPagingResponseDto> getBuyerPaging(int page) {
		Page<Buyer> buyerList = buyerRepository.findAll(PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id")));
		return buyerList.map(buyer -> buyerMapper.toBuyerPagingResponseDto(buyer));
	}

	public BuyerDetailResponseDto getBuyerDetail(Long id) {
		return buyerMapper.toBuyerDetailResponseDto(getBuyerId(id));
	}
}
