package com.bancow.bancowback.domain.sub.farm.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.ErrorCode;

import com.bancow.bancowback.domain.common.exception.FarmException;

import com.bancow.bancowback.domain.manager.entity.Manager;
import com.bancow.bancowback.domain.sub.farm.dto.FarmAddRequestDto;
import com.bancow.bancowback.domain.sub.farm.dto.FarmDetailResponseDto;
import com.bancow.bancowback.domain.sub.farm.dto.FarmDistributeResponseDto;
import com.bancow.bancowback.domain.sub.farm.dto.FarmPagingResponseDto;
import com.bancow.bancowback.domain.sub.farm.dto.FarmUpdateRequestDto;
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

	public List<FarmDistributeResponseDto> getFarmDistribute() {
		List<Farm> farmList = farmRepository.findByStatus(true);

		if (farmList.size() == 0) {
			throw new FarmException(ErrorCode.NOT_FOUND_FARM, "농장 없음");
		}

		return farmList.stream().map(farm -> farmMapper.toDistributeResponseDto(farm)).collect(Collectors.toList());

	}

	public Page<FarmPagingResponseDto> getFarmPaging(int page) {
		Page<Farm> farmList = farmRepository.findAll(PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id")));
		return farmList.map(farm -> farmMapper.toFarmPagingResponseDto(farm));
	}

	public FarmDetailResponseDto getFarmDetail(Long id) {
		return farmMapper.toFarmDetailResponseDto(getFarmId(id));
	}

	public Farm getFarmId(Long id) {
		return farmRepository.findById(id)
			.orElseThrow(() -> new FarmException(ErrorCode.NOT_FOUND_FARM, "농장 없음"));
	}

	public ServiceResult editFarmImage(FarmInfo<FarmUpdateRequestDto> farmInfo) {
		Farm farm = farmMapper.toUpdateEntity(farmInfo.getManager(), getFarmId(farmInfo.getDto().getId()),
			farmInfo.getDto());
		farmRepository.save(farm);
		return ServiceResult.success("농장이 수정 됐습니다.");
	}

	public ServiceResult editFarmNotImage(Manager manager, FarmUpdateRequestDto farmUpdateRequestDto) {
		Farm farm = farmMapper.toUpdateNotImageEntity(manager, getFarmId(farmUpdateRequestDto.getId()),
			farmUpdateRequestDto);
		farmRepository.save(farm);
		return ServiceResult.success("농장이 수정 됐습니다.");
	}

	public ServiceResult deleteFarmOne(Long id) {
		farmRepository.delete(getFarmId(id));
		return ServiceResult.success("이벤트가 삭제 됐습니다.");
	}
}
