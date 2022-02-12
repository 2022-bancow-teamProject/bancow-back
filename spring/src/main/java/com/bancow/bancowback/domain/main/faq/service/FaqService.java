package com.bancow.bancowback.domain.main.faq.service;

import static com.bancow.bancowback.domain.common.exception.ErrorCode.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.FaqException;
import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.main.faq.entity.Faq;
import com.bancow.bancowback.domain.main.faq.mapper.FaqMapper;
import com.bancow.bancowback.domain.main.faq.dto.FaqAddDto;
import com.bancow.bancowback.domain.main.faq.dto.FaqResponseDto;
import com.bancow.bancowback.domain.main.faq.dto.FaqUpdateDto;
import com.bancow.bancowback.domain.main.faq.repository.FaqRepository;
import com.bancow.bancowback.domain.manager.entity.Manager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FaqService {
	private final FaqRepository faqRepository;
	private final FaqMapper faqMapper;
	private final TokenService tokenService;

	public FaqResponseDto getPublicFaq(Long id) {
		return faqMapper.toResponseDto(getFaqId(id));
	}

	public Page<FaqResponseDto> getPublicFaqPaging(int page) {
		Page<Faq> faqList = faqRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));
		return faqList.map(faq -> faqMapper.toResponseDto(faq));
	}

	public FaqResponseDto getFaq(String token, Long id) {
		tokenService.validTokenAuthority(token);
		return faqMapper.toResponseDto(getFaqId(id));
	}

	public Faq getFaqId(Long id){
		return faqRepository.findById(id)
			.orElseThrow(() -> new FaqException(NOT_FOUND_FAQ, "해당 공지사항 없음"));
	}

	public Page<FaqResponseDto> getFaqPaging(String token, int page) {
		tokenService.validTokenAuthority(token);
		Page<Faq> faqList = faqRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));
		return faqList.map(faq -> faqMapper.toResponseDto(faq));
	}

	public ServiceResult addFaq(String token, FaqAddDto faqAddDto) {
		tokenService.validTokenAuthority(token);
		Manager manager = tokenService.getManager(token);
		Faq faq = faqMapper.toAdd(manager, faqAddDto);
		faqRepository.save(faq);
		return ServiceResult.success("공지사항 등록 성공!");
	}

	public ServiceResult deleteFaq(String token, Long id) {
		tokenService.validTokenAuthority(token);
		faqRepository.delete(getFaqId(id));
		return ServiceResult.success("공지사항 삭제 성공!");
	}

	public ServiceResult deleteFaqList(String token, List<Long> id) {
		tokenService.validTokenAuthority(token);

		List<Faq> deleteFaqList = faqRepository.findByIdIn(id);

		if (deleteFaqList.size() == 0) {
			throw new FaqException(NOT_FOUND_FAQ, "해당 공지사항 없음");
		}

		deleteFaqList.stream().forEach(e -> {
			faqRepository.delete(e);
		});
		return ServiceResult.success("공지사항 삭제 성공!");
	}

	public ServiceResult updateFaq(String token, FaqUpdateDto faqUpdateDto) {
		tokenService.validTokenAuthority(token);
		Manager manager = tokenService.getManager(token);
		Faq faq = faqRepository.findById(faqUpdateDto.getId())
			.orElseThrow(() -> new FaqException(NOT_FOUND_FAQ, "해당 팝업 없음"));
		faq = faqMapper.toUpdate(manager, faq, faqUpdateDto);
		faqRepository.save(faq);
		return ServiceResult.success("공지사항 수정 성공!");
	}
}
