package com.bancow.bancowback.domain.main.notice.service;

import static com.bancow.bancowback.domain.common.exception.ErrorCode.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.NoticeException;
import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.main.notice.dto.NoticeDeleteListDto;
import com.bancow.bancowback.domain.main.notice.dto.NoticeRequestDto;
import com.bancow.bancowback.domain.main.notice.entity.Notice;
import com.bancow.bancowback.domain.main.notice.mapper.NoticeMapper;
import com.bancow.bancowback.domain.main.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	private final NoticeRepository noticeRepository;
	private final NoticeMapper noticeMapper;
	private final TokenService tokenService;

	public Notice getNotice(String token, Long id) {
		tokenService.validTokenAuthority(token);
		Notice notice = noticeRepository.findById(id)
			.orElseThrow(() -> new NoticeException(NOT_FOUND_NOTICE, "해당 ID의 Notice를 찾을 수 없습니다."));

		return noticeRepository.save(notice);
	}

	public Page<Notice> getNoticePaging(int page, String token) {
		tokenService.validTokenAuthority(token);

		return noticeRepository.findAll(
			PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"))
		);
	}

	public Notice addNotice(String token, NoticeRequestDto noticeRequestDto) {
		tokenService.validTokenAuthority(token);
		Notice notice = noticeMapper.toEntity(noticeRequestDto);
		return noticeRepository.save(notice);
	}

	public ServiceResult deleteNotice(String token, Long id) {

		tokenService.validTokenAuthority(token);
		Notice notice = noticeRepository.findById(id)
			.orElseThrow(() -> new NoticeException(NOT_FOUND_NOTICE, "해당 ID의 Notice를 찾을 수 없습니다."));

		noticeRepository.delete(notice);
		return ServiceResult.success("Notice를 성공적으로 삭제했습니다.");
	}

	public ServiceResult deleteNoticeList(String token, NoticeDeleteListDto noticeDeleteListInput) {
		tokenService.validTokenAuthority(token);
		Optional<List<Notice>> optionalNoticeList = noticeRepository.findByIdIn(noticeDeleteListInput.getIdList());
		List<Notice> noticeList = optionalNoticeList.get();

		if (noticeList.size() == 0) {
			throw new NoticeException(NOT_FOUND_NOTICE, "해당 공지사항 없음");
		}
		noticeList.stream().forEach(e -> {
			noticeRepository.delete(e);
		});
		return ServiceResult.success("글 삭제 성공.");
	}

	public ServiceResult updateNotice(String token, Long id, NoticeRequestDto noticeInput) {

		tokenService.validTokenAuthority(token);
		Notice notice = noticeRepository.findById(id)
			.orElseThrow(() -> new NoticeException(NOT_FOUND_NOTICE, "해당 ID의 Notice를 찾을 수 없습니다."));

		notice.setNoticeCategory(noticeInput.getNoticeCategory());
		notice.setUsername(noticeInput.getUsername());
		notice.setTitle(noticeInput.getTitle());
		notice.setMessage(noticeInput.getMessage());
		notice.setStatus(noticeInput.isStatus());
		notice.setUpdateDate(LocalDateTime.now());

		noticeRepository.save(notice);
		return ServiceResult.success("글 수정 성공.");
	}
}
