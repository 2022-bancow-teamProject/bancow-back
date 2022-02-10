package com.bancow.bancowback.domain.main.farmqna.service;

import static com.bancow.bancowback.domain.common.exception.ErrorCode.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.FarmQnaException;
import com.bancow.bancowback.domain.common.util.token.service.TokenService;
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
	private final TokenService tokenService;

	public ServiceResult addFarmQna(FarmQnaAddRequestDto dto) {
		FarmQna farmQna = farmQnaMapper.toEntity(dto);
		farmQnaRepository.save(farmQna);
		return ServiceResult.success("Farm Qna가 성공적으로 등록되었습니다.");
	}

	public FarmQna getFarmQna(String token, Long id) {
		tokenService.validTokenAuthority(token);
		FarmQna farmQna = farmQnaRepository.findById(id)
			.orElseThrow(() -> new FarmQnaException(NOT_FOUND_FARM_QNA, "해당 Id의 농가 입점 문의를 찾을 수 없습니다."));
		return farmQnaRepository.save(farmQna);
	}

	public Page<FarmQna> getFarmQnaPaging(int page, String token) {
		tokenService.validTokenAuthority(token);

		return farmQnaRepository.findAll(
			PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"))
		);
	}
}
