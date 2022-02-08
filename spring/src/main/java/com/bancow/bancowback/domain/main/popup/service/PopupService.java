package com.bancow.bancowback.domain.main.popup.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.ErrorCode;
import com.bancow.bancowback.domain.common.exception.PopupException;
import com.bancow.bancowback.domain.main.popup.dto.PopupAddRequestDto;
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

	public ServiceResult editPopupImage(PopupInfo<PopupUpdateRequestDto> dto) {
		Popup popup = popupMapper.toUpdateEntity(getPopupId(dto.getDto().getId()), dto.getDto(), dto.getImagePath());
		popupRepository.save(popup);
		return ServiceResult.success("팝업이 업데이트가 되었습니다.");
	}

	public ServiceResult editPopupNotImage(PopupUpdateRequestDto dto) {
		Popup popup = popupMapper.toUpdateNotImageEntity(getPopupId(dto.getId()),dto);
		popupRepository.save(popup);
		return ServiceResult.success("팝업이 업데이트가 되었습니다.");
	}
}
