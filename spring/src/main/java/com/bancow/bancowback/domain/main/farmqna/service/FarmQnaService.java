package com.bancow.bancowback.domain.main.farmqna.service;

import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.main.farmqna.dto.FarmQnaAddRequestDto;
import com.bancow.bancowback.domain.main.farmqna.entity.FarmQna;
import com.bancow.bancowback.domain.main.farmqna.mapper.FarmQnaMapper;
import com.bancow.bancowback.domain.main.farmqna.repository.FarmQnaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FarmQnaService {

	private final FarmQnaRepository farmQnaRepository;
	private final FarmQnaMapper farmQnaMapper;

	public ServiceResult addFarmQna(FarmQnaAddRequestDto dto) {
		FarmQna farmQna = farmQnaMapper.toEntity(dto);
		farmQnaRepository.save(farmQna);
		return ServiceResult.success("Farm Qna가 성공적으로 등록되었습니다.");
	}
}
