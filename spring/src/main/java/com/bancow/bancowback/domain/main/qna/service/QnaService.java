package com.bancow.bancowback.domain.main.qna.service;

import static com.bancow.bancowback.domain.common.exception.ErrorCode.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.QnaException;
import com.bancow.bancowback.domain.common.util.mail.service.MailService;
import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.main.qna.dto.QnaReplyDto;
import com.bancow.bancowback.domain.main.qna.dto.QnaRequestDto;
import com.bancow.bancowback.domain.main.qna.entity.Qna;
import com.bancow.bancowback.domain.main.qna.entity.QnaReply;
import com.bancow.bancowback.domain.main.qna.mapper.QnaMapper;
import com.bancow.bancowback.domain.main.qna.mapper.QnaReplyMapper;
import com.bancow.bancowback.domain.main.qna.repository.QnaReplyRepository;
import com.bancow.bancowback.domain.main.qna.repository.QnaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaService {
	private final QnaRepository qnaRepository;
	private final QnaMapper qnaMapper;
	private final TokenService tokenService;
	private final MailService mailService;

	private final QnaReplyRepository qnaReplyRepository;
	private final QnaReplyMapper qnaReplyMapper;

	public Qna getQna(String token, Long qnaId) {
		tokenService.validTokenAuthority(token);
		Qna qna = qnaRepository.findById(qnaId)
			.orElseThrow(() -> new QnaException(NOT_Found_QNA, "해당 ID의 QnA를 찾을 수 없습니다."));

		return qnaRepository.save(qna);
	}

	public Page<Qna> getQnaPaging(int page, String token) {
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

	public Qna addQna(QnaRequestDto qnaRequestDto) {
		Qna qna = qnaMapper.toEntity(qnaRequestDto);
		return qnaRepository.save(qna);
	}

	public ServiceResult replyQna(QnaReplyDto dto, String token, Long id) {
		tokenService.validTokenAuthority(token);
		Qna qna = qnaRepository.findById(id)
			.orElseThrow(() -> new QnaException(NOT_Found_QNA, "해당 ID의 QnA를 찾을 수 없습니다."));
		mailService.sendQnaReplyMail(qna, dto, "QNA_REPLY");

		QnaReply qnaReply = qnaReplyMapper.toEntity(dto, qna, tokenService.getManager(token));
		qna.setChecked(true);
		qnaReplyRepository.save(qnaReply);

		return ServiceResult.success("QnA 답변 이메일 보내기를 성공하였습니다.");
	}

	public Integer countQna() {
		return qnaRepository.countQna();
	}

	public Integer uncheckedQna() {
		return qnaRepository.uncheckedQna();
	}
}
