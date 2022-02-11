package com.bancow.bancowback.domain.sub.chart.service;

import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.main.farmqna.service.FarmQnaService;
import com.bancow.bancowback.domain.main.qna.service.QnaService;
import com.bancow.bancowback.domain.sub.chart.dto.QnaNumResponseDto;
import com.bancow.bancowback.domain.sub.chart.mapper.QnaChartMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChartService {

	private final QnaService qnaService;
	private final FarmQnaService farmQnaService;
	private final TokenService tokenService;

	private final QnaChartMapper mapper;

	public QnaNumResponseDto qnaNum(String token) {
		tokenService.validTokenAuthority(token);

		Integer qnaNum = qnaService.countQna();
		Integer uncheckedQna = qnaService.uncheckedQna();

		Integer farmQnaNum = farmQnaService.countFarmQna();
		Integer uncheckedFarmQna = farmQnaService.uncheckedFarmQna();

		return mapper.toResponse(qnaNum, uncheckedQna, farmQnaNum, uncheckedFarmQna);
	}

}
