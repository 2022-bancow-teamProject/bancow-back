package com.bancow.bancowback.domain.main.qna.service;

import static com.bancow.bancowback.domain.common.exception.ErrorCode.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

	public Page<Qna> getAllQna(int page, String token) {
		tokenService.validTokenAuthority(token);

		return qnaRepository.findAll(
			PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"))
		);
	}

	public ServiceResult deleteQna(String token, Long id) {
		tokenService.validTokenAuthority(token);
		Qna qna = qnaRepository.findById(id)
			.orElseThrow(() -> new QnaException(NOT_Found_QNA, "해당 ID의 QnA를 찾을 수 없습니다."));

		qnaRepository.delete(qna);
		return ServiceResult.success("QnA를 성공적으로 삭제하였습니다.");
	}
}
