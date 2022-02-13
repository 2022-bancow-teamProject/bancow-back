package com.bancow.bancowback.domain.main.history.service;

import static com.bancow.bancowback.domain.common.exception.ErrorCode.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.HistoryException;
import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.main.history.dto.HistoryAddDto;
import com.bancow.bancowback.domain.main.history.dto.HistoryResponseDto;
import com.bancow.bancowback.domain.main.history.dto.HistoryUpdateDto;
import com.bancow.bancowback.domain.main.history.entity.History;
import com.bancow.bancowback.domain.main.history.mapper.HistoryMapper;
import com.bancow.bancowback.domain.main.history.repository.HistoryRepository;
import com.bancow.bancowback.domain.manager.entity.Manager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {
	private final HistoryRepository historyRepository;
	private final HistoryMapper historyMapper;
	private final TokenService tokenService;

	public HistoryResponseDto getPublicHistory(Long id) {
		return historyMapper.toResponseDto(getHistoryId(id));
	}

	public Page<HistoryResponseDto> getPublicHistoryPaging(int page) {
		Page<History> historyList = historyRepository.findAll(PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id")));
		return historyList.map(history -> historyMapper.toResponseDto(history));
	}

	public HistoryResponseDto getHistory(String token, Long id) {
		tokenService.validTokenAuthority(token);
		return historyMapper.toResponseDto(getHistoryId(id));
	}

	public History getHistoryId(Long id){
		return historyRepository.findById(id)
			.orElseThrow(() -> new HistoryException(NOT_FOUND_HISTORY, "해당 히스토리 없음"));
	}

	public Page<HistoryResponseDto> getHistoryPaging(String token, int page) {
		tokenService.validTokenAuthority(token);
		Page<History> historyList = historyRepository.findAll(PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id")));
		return historyList.map(history -> historyMapper.toResponseDto(history));
	}

	public ServiceResult addHistory(String token, HistoryAddDto historyAddDto) {
		tokenService.validTokenAuthority(token);
		Manager manager = tokenService.getManager(token);
		History history = historyMapper.toAdd(manager, historyAddDto);
		historyRepository.save(history);
		return ServiceResult.success("HISTORY 등록 성공!");
	}

	public ServiceResult deleteHistory(String token, Long id) {
		tokenService.validTokenAuthority(token);
		historyRepository.delete(getHistoryId(id));
		return ServiceResult.success("HISTORY 삭제 성공!");
	}

	public ServiceResult deleteHistoryList(String token, List<Long> id) {
		tokenService.validTokenAuthority(token);

		List<History> deleteHistoryList = historyRepository.findByIdIn(id);

		if (deleteHistoryList.size() == 0) {
			throw new HistoryException(NOT_FOUND_HISTORY, "해당 HISTORY 없음");
		}

		deleteHistoryList.stream().forEach(e -> {
			historyRepository.delete(e);
		});
		return ServiceResult.success("HISTORY 삭제 성공!");
	}

	public ServiceResult updateHistory(String token, HistoryUpdateDto historyUpdateDto) {
		tokenService.validTokenAuthority(token);
		Manager manager = tokenService.getManager(token);
		History history = historyRepository.findById(historyUpdateDto.getId())
			.orElseThrow(() -> new HistoryException(NOT_FOUND_HISTORY, "해당 팝업 없음"));
		history = historyMapper.toUpdate(manager, history, historyUpdateDto);
		historyRepository.save(history);
		return ServiceResult.success("HISTORY 수정 성공!");
	}
}
