package com.bancow.bancowback.domain.main.notice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.main.notice.dto.NoticeDeleteListDto;
import com.bancow.bancowback.domain.main.notice.dto.NoticeInputDto;
import com.bancow.bancowback.domain.main.notice.entity.Notice;
import com.bancow.bancowback.domain.main.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;

	public Notice getNotice(@PathVariable Long id) {

		Optional<Notice> optionalNotice = noticeRepository.findById(id);
		if(!optionalNotice.isPresent()){
			return null;
		}
		Notice notice = optionalNotice.get();
		return noticeRepository.save(notice);
	}

	public List<Notice> getNoticeList(){

		return noticeRepository.findAll();
	}

	public ServiceResult addNotice(@RequestBody NoticeInputDto noticeInput) {

		Notice notice = Notice.builder()
			.noticeCategory(noticeInput.getNoticeCategory())
			.username(noticeInput.getUsername())
			.title(noticeInput.getTitle())
			.message(noticeInput.getMessage())
			.status(noticeInput.isStatus())
			.createDate(LocalDateTime.now())
			.build();

		noticeRepository.save(notice);
		return ServiceResult.success("글 등록 성공!");
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


	public ServiceResult updateNotice(@PathVariable Long id, @RequestBody NoticeInputDto noticeInput){

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
