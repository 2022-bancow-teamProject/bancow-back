package com.bancow.bancowback.domain.main.history.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.main.history.dto.HistoryInputDto;
import com.bancow.bancowback.domain.main.history.dto.HistoryDeleteListDto;
import com.bancow.bancowback.domain.main.history.entity.History;
import com.bancow.bancowback.domain.main.history.repository.HistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {

	private final HistoryRepository historyRepository;

	public History getHistory(@PathVariable Long id) {

		Optional<History> optionalBancowHistory = historyRepository.findById(id);
		if (!optionalBancowHistory.isPresent()) {
			return null;
		}
		History bancowHistory = optionalBancowHistory.get();
		return historyRepository.save(bancowHistory);
	}

	public List<History> getHistoryList() {

		return historyRepository.findAll();
	}

	public ServiceResult addHistory(@RequestBody HistoryInputDto historyInputDto) {

		History bancowHistory = History.builder()
			.historyDate(historyInputDto.getHistoryDate())
			.createDate(LocalDateTime.now())
			.status(historyInputDto.isStatus())
			.message(historyInputDto.getMessage())
			.username(historyInputDto.getUserName())
			.build();

		historyRepository.save(bancowHistory);
		return ServiceResult.success("글 등록 성공!");
	}

	public ServiceResult deleteBancowHistory(@PathVariable Long id) {

		Optional<History> optionalHistory = historyRepository.findById(id);
		if (!optionalHistory.isPresent()) {
			return ServiceResult.fail("존재하지 않는 게시글입니다.");
		}
		History bancowHistory = optionalHistory.get();

		historyRepository.delete(bancowHistory);
		return ServiceResult.success("글 삭제 성공");
	}

	public ServiceResult deleteHistoryList(@RequestBody HistoryDeleteListDto historyDeleteListDto){

		Optional<List<History>> optionalHistoryList = historyRepository.findByIdIn(historyDeleteListDto.getIdList());
		List<History> historyList = optionalHistoryList.get();
		if(!optionalHistoryList.isPresent()){
			return ServiceResult.fail("존재하지 않는 게시글입니다.");
		}

		historyList.stream().forEach(e -> {
			historyRepository.delete(e);
		});
		return ServiceResult.success("글 삭제 성공!");

	}


	public ServiceResult updateHistory(@PathVariable Long id, @RequestBody HistoryInputDto historyInputDto) {

		Optional<History> history = historyRepository.findById(id);
		if (!history.isPresent()) {
			return ServiceResult.fail("존재하지 않는 게시글입니다.");
		}
		history.get().setHistoryDate(historyInputDto.getHistoryDate());
		history.get().setUpdateDate(LocalDateTime.now());
		history.get().setMessage(historyInputDto.getMessage());
		history.get().setStatus(historyInputDto.isStatus());
		history.get().setUsername(historyInputDto.getUserName());

		historyRepository.save(history.get());
		return ServiceResult.success("글 수정 성공.");
	}

}

