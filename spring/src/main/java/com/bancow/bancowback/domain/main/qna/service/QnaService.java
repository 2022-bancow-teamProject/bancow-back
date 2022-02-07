package com.bancow.bancowback.domain.main.qna.service;

import static com.bancow.bancowback.domain.common.exception.ErrorCode.*;

import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.QnaException;
import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.main.qna.entity.Qna;
import com.bancow.bancowback.domain.main.qna.repository.QnaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaService {
	private final QnaRepository qnaRepository;
	private final TokenService tokenService;

	public Qna getQna(String token, Long qnaId) {
		tokenService.validTokenAuthority(token);
		Qna qna = qnaRepository.findById(qnaId)
			.orElseThrow(() -> new QnaException(NOT_Found_QNA, "해당 ID의 QnA를 찾을 수 없습니다."));

		return qnaRepository.save(qna);
	}
}
