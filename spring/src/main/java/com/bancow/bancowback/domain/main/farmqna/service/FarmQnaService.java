package com.bancow.bancowback.domain.main.farmqna.service;

import static com.bancow.bancowback.domain.common.exception.ErrorCode.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.FarmQnaException;
import com.bancow.bancowback.domain.common.util.mail.service.MailService;
import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.main.farmqna.dto.FarmQnaAddRequestDto;
import com.bancow.bancowback.domain.main.farmqna.dto.FarmQnaDeleteRequestDto;
import com.bancow.bancowback.domain.main.farmqna.dto.FarmQnaReplyDto;
import com.bancow.bancowback.domain.main.farmqna.entity.FarmQna;
import com.bancow.bancowback.domain.main.farmqna.entity.FarmQnaReply;
import com.bancow.bancowback.domain.main.farmqna.mapper.FarmQnaMapper;
import com.bancow.bancowback.domain.main.farmqna.mapper.FarmQnaReplyMapper;
import com.bancow.bancowback.domain.main.farmqna.repository.FarmQnaReplyRepository;
import com.bancow.bancowback.domain.main.farmqna.repository.FarmQnaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FarmQnaService {

	private final FarmQnaRepository farmQnaRepository;
	private final FarmQnaMapper farmQnaMapper;
	private final TokenService tokenService;
	private final MailService mailService;

	private final FarmQnaReplyRepository farmQnaReplyRepository;
	private final FarmQnaReplyMapper farmQnaReplyMapper;

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

	public ServiceResult deleteFarmQna(String token, Long id) {
		tokenService.validTokenAuthority(token);
		FarmQna farmQna = farmQnaRepository.findById(id)
			.orElseThrow(() -> new FarmQnaException(NOT_FOUND_FARM_QNA, "해당 Id의 농가 입점 문의를 찾을 수 없습니다."));
		farmQnaRepository.delete(farmQna);
		return ServiceResult.success("해당 농가입점 문의를 성공적으로 삭제하였습니다.");
	}

	public ServiceResult deleteFarmQnaList(String token, FarmQnaDeleteRequestDto dto) {
		tokenService.validTokenAuthority(token);
		List<FarmQna> deleteList = farmQnaRepository.findByIdIn(dto.getId());
		if (deleteList.size() == 0) {
			throw new FarmQnaException(NOT_FOUND_FARM_QNA, "해당 농가 입점 문의가 업습니다.");
		}
		deleteList.stream()
			.forEach(farmQnaRepository::delete);

		return ServiceResult.success("해당 농가입점 문의를 성공적으로 삭제하였습니다.");
	}

	public ServiceResult replyFarmQna(FarmQnaReplyDto dto, String token, Long id) {
		tokenService.validTokenAuthority(token);
		FarmQna farmQna = farmQnaRepository.findById(id)
			.orElseThrow(() -> new FarmQnaException(NOT_FOUND_FARM_QNA, "해당 Id의 농가 입점 문의를 찾을 수 없습니다."));
		mailService.sendReplyMail(farmQna, dto, "FARM_QNA_REPLY");

		FarmQnaReply farmQnaReply = farmQnaReplyMapper.toEntity(dto, farmQna, tokenService.getManager(token));
		farmQna.setChecked(true);
		farmQnaReplyRepository.save(farmQnaReply);

		return ServiceResult.success("농가입점 답변 이메일 보내기를 성공하였습니다.");
	}

	public Integer countFarmQna() {
		return farmQnaRepository.countFarmQna();
	}

	public Integer uncheckedFarmQna() {
		return farmQnaRepository.uncheckedFarmQna();
	}

	public List<Map<String, Object>> countMonth(int year) {
		return farmQnaRepository.countMonth(year);
	}
}
