package com.bancow.bancowback.domain.main.popup.service;

import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.ErrorCode;
import com.bancow.bancowback.domain.common.exception.PopupException;
import com.bancow.bancowback.domain.main.popup.dto.PopupAddRequestDto;
import com.bancow.bancowback.domain.main.popup.dto.PopupResponseDto;
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

}
