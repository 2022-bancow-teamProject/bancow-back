package com.bancow.bancowback.domain.sub.farm.service;

import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.sub.farm.dto.FarmAddRequestDto;
import com.bancow.bancowback.domain.sub.farm.entity.Farm;
import com.bancow.bancowback.domain.sub.farm.entity.FarmInfo;
import com.bancow.bancowback.domain.sub.farm.mapper.FarmMapper;
import com.bancow.bancowback.domain.sub.farm.repository.FarmRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FarmService {

	private final FarmRepository farmRepository;
	private final FarmMapper farmMapper;

	public ServiceResult addFarm(FarmInfo farmInfo) {
		Farm farm = farmMapper.toEntity(farmInfo.getManager(), (FarmAddRequestDto)farmInfo.getDto());
		farmRepository.save(farm);

		return ServiceResult.success("농장이 등록 됐습니다.");
	}
}
