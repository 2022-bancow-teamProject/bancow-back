package com.bancow.bancowback.domain.main.popup.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.ErrorCode;
import com.bancow.bancowback.domain.common.exception.PopupException;
import com.bancow.bancowback.domain.main.popup.dto.PopupAddRequestDto;
import com.bancow.bancowback.domain.main.popup.dto.PopupDistributeResponseDto;
import com.bancow.bancowback.domain.main.popup.dto.PopupResponseDto;
import com.bancow.bancowback.domain.main.popup.dto.PopupUpdateRequestDto;
import com.bancow.bancowback.domain.main.popup.entity.Popup;
import com.bancow.bancowback.domain.main.popup.dto.PopupInfo;
import com.bancow.bancowback.domain.main.popup.mapper.PopupMapper;
import com.bancow.bancowback.domain.main.popup.repository.PopupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PopupService {

	private final PopupRepository popupRepository;
	private final PopupMapper popupMapper;

	public ServiceResult addPopup(PopupInfo popupInfo) {
		Popup popup = popupMapper.toEntity(popupInfo.getManager(), (PopupAddRequestDto)popupInfo.getDto(),
			popupInfo.getImagePath());
		popupRepository.save(popup);
		return ServiceResult.success("팝업이 등록 됐습니다.");
	}

	public PopupResponseDto getPopupDetail(Long id) {
		return popupMapper.toResponseDto(getPopupId(id));
	}

	public Popup getPopupId(Long id) {
		return popupRepository.findById(id)
			.orElseThrow(() -> new PopupException(ErrorCode.NOT_FOUND_POPUP, "해당 팝업 없음"));
	}

	public Page<PopupResponseDto> getPopupPaging(int page) {
		Page<Popup> popupList = popupRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));
		return popupList.map(popup -> popupMapper.toResponseDto(popup));
	}

	public void getPopupStatusFalse(Boolean status){
		if(status == Boolean.TRUE){
			Popup popup = popupMapper.toPopupStatusFalse(popupRepository.findByStatus(status));
			popupRepository.save(popup);
		}
	}

	public ServiceResult editPopupImage(PopupInfo<PopupUpdateRequestDto> PopupInfo) {
		getPopupStatusFalse(PopupInfo.getDto().getStatus());
		Popup popup = popupMapper.toUpdateEntity(getPopupId(PopupInfo.getDto().getId()), PopupInfo.getDto(), PopupInfo.getImagePath());
		popupRepository.save(popup);
		return ServiceResult.success("팝업이 업데이트가 되었습니다.");
	}

	public ServiceResult editPopupNotImage(PopupUpdateRequestDto PopupUpdateRequestDto) {
		getPopupStatusFalse(PopupUpdateRequestDto.getStatus());
		Popup popup = popupMapper.toUpdateNotImageEntity(getPopupId(PopupUpdateRequestDto.getId()),PopupUpdateRequestDto);
		popupRepository.save(popup);
		return ServiceResult.success("팝업이 업데이트가 되었습니다.");
	}

	public ServiceResult deletePopupOne(Long id) {
		popupRepository.delete(getPopupId(id));
		return ServiceResult.success("팝업이 삭제 되었습니다.");
	}

	public ServiceResult deletePopupList(List<Long> id) {
		List<Popup> deletePopupList = popupRepository.findByIdIn(id);

		if(deletePopupList.size() == 0){
			throw new PopupException(ErrorCode.NOT_FOUND_POPUP, "해당 팝업 없음");
		}

		deletePopupList
			.stream().forEach(e -> {popupRepository.delete(e);
		});

		return ServiceResult.success("팝업 삭제 성공.");
	}
}
