package com.bancow.bancowback.domain.main.notice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.ErrorCode;
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
			.orElseThrow(() -> new NoticeException(ErrorCode.NOT_FOUND_NOTICE, "해당 ID의 Notice를 찾을 수 없습니다."));

		return noticeRepository.save(notice);
	}

	public Page<Notice> getNoticePaging(int page, String token){
		tokenService.validTokenAuthority(token);

		return noticeRepository.findAll(
			PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"))
		);
	}

	public Notice addNotice(NoticeRequestDto noticeRequestDto) {
		Notice notice = noticeMapper.toEntity(noticeRequestDto);
		return noticeRepository.save(notice);
	}

	public ServiceResult deleteNotice(@PathVariable Long id) {

		Optional<Notice> optionalNotice = noticeRepository.findById(id);
		if(!optionalNotice.isPresent()){
			return ServiceResult.fail("존재하지 않는 게시글입니다.");
		}
		Notice notice = optionalNotice.get();

		noticeRepository.delete(notice);
		return ServiceResult.success("글 삭제 성공.");
	}


	public ServiceResult deleteNoticeList(@RequestBody NoticeDeleteListDto noticeDeleteListInput){
		Optional<List<Notice>> optionalNoticeList = noticeRepository.findByIdIn(noticeDeleteListInput.getIdList());
		List<Notice> noticeList = optionalNoticeList.get();
		if(!optionalNoticeList.isPresent()){
			return ServiceResult.fail("존재하지 않는 게시글입니다.");
		}

		noticeList.stream().forEach(e -> {
			noticeRepository.delete(e);
		});
		return ServiceResult.success("글 삭제 성공.");

	}


	public ServiceResult updateNotice(@PathVariable Long id, @RequestBody NoticeRequestDto noticeInput){

		Optional<Notice> notice = noticeRepository.findById(id);
		if(!notice.isPresent()){
			return ServiceResult.fail("존재하지 않는 게시글입니다.");
		}
		notice.get().setNoticeCategory(noticeInput.getNoticeCategory());
		notice.get().setUsername(noticeInput.getUsername());
		notice.get().setTitle(noticeInput.getTitle());
		notice.get().setMessage(noticeInput.getMessage());
		notice.get().setStatus(noticeInput.isStatus());
		notice.get().setUpdateDate(LocalDateTime.now());

		noticeRepository.save(notice.get());
		return ServiceResult.success("글 수정 성공.");
	}
}
