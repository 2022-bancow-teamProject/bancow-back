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
import com.bancow.bancowback.domain.main.notice.dto.NoticeAddDto;
import com.bancow.bancowback.domain.main.notice.dto.NoticeDeleteListDto;
import com.bancow.bancowback.domain.main.notice.dto.NoticeResponseDto;
import com.bancow.bancowback.domain.main.notice.dto.NoticeUpdateDto;
import com.bancow.bancowback.domain.main.notice.entity.Notice;
import com.bancow.bancowback.domain.main.notice.mapper.NoticeMapper;
import com.bancow.bancowback.domain.main.notice.repository.NoticeRepository;
import com.bancow.bancowback.domain.manager.entity.Manager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	private final NoticeRepository noticeRepository;
	private final NoticeMapper noticeMapper;
	private final TokenService tokenService;

	public NoticeResponseDto getPublicNotice(Long id) {
		return noticeMapper.toResponseDto(getNoticeId(id));
	}

	public Page<NoticeResponseDto> getPublicNoticePaging(int page) {
		Page<Notice> noticeList = noticeRepository.findAll(PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id")));
		return noticeList.map(notice -> noticeMapper.toResponseDto(notice));
	}

	public NoticeResponseDto getNotice(String token, Long id) {
		tokenService.validTokenAuthority(token);
		return noticeMapper.toResponseDto(getNoticeId(id));
	}

	public Notice getNoticeId(Long id){
		return noticeRepository.findById(id)
			.orElseThrow(() -> new NoticeException(NOT_FOUND_NOTICE, "해당 공지사항 없음"));
	}

	public Page<NoticeResponseDto> getNoticePaging(String token, int page) {
		tokenService.validTokenAuthority(token);
		Page<Notice> noticeList = noticeRepository.findAll(PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id")));
		return noticeList.map(notice -> noticeMapper.toResponseDto(notice));
	}

	public ServiceResult addNotice(String token, NoticeAddDto noticeAddDto) {
		tokenService.validTokenAuthority(token);
		Manager manager = tokenService.getManager(token);
		Notice notice = noticeMapper.toAdd(manager, noticeAddDto);
		noticeRepository.save(notice);
		return ServiceResult.success("공지사항 등록 성공!");
	}

	public ServiceResult deleteNotice(String token, Long id) {
		tokenService.validTokenAuthority(token);
		noticeRepository.delete(getNoticeId(id));
		return ServiceResult.success("공지사항 삭제 성공!");
	}

	public ServiceResult deleteNoticeList(String token, List<Long> id) {
		tokenService.validTokenAuthority(token);

		List<Notice> deleteNoticeList = noticeRepository.findByIdIn(id);

		if (deleteNoticeList.size() == 0) {
			throw new NoticeException(NOT_FOUND_NOTICE, "해당 공지사항 없음");
		}

		deleteNoticeList.stream().forEach(e -> {
			noticeRepository.delete(e);
		});
		return ServiceResult.success("공지사항 삭제 성공!");
	}

	public ServiceResult updateNotice(String token, NoticeUpdateDto noticeUpdateDto) {
		tokenService.validTokenAuthority(token);
		Manager manager = tokenService.getManager(token);
		Notice notice = noticeRepository.findById(noticeUpdateDto.getId())
			.orElseThrow(() -> new NoticeException(NOT_FOUND_NOTICE, "해당 팝업 없음"));
		notice = noticeMapper.toUpdate(manager, notice, noticeUpdateDto);
		noticeRepository.save(notice);
		return ServiceResult.success("공지사항 수정 성공!");
	}
}
