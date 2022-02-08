package com.bancow.bancowback.domain.main.history.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.main.history.dto.HistoryInputDto;
import com.bancow.bancowback.domain.main.history.dto.HistoryDeleteListDto;
import com.bancow.bancowback.domain.main.history.entity.History;
import com.bancow.bancowback.domain.main.history.service.HistoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/history")
public class HistoryController {
	private final HistoryService historyService;

	@GetMapping("/{id}")
	public Response<?> getHistory(@PathVariable Long id) {

		History bancowHistory = historyService.getHistory(id);
		if (bancowHistory == null) {
			return new Response<>(null, HttpStatus.BAD_REQUEST);
		}
		return new Response<>(bancowHistory, HttpStatus.OK);
	}

	@GetMapping("/historylist")
	public Response<?> getHistoryList() {

		List<History> historyList = historyService.getHistoryList();
		return new Response<>(historyList, HttpStatus.OK);
	}

	@PostMapping("/add")
	public Response<?> addHistory(@RequestBody HistoryInputDto bancowHistoryInputDto) {

		ServiceResult result = historyService.addHistory(bancowHistoryInputDto);
		return new Response<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public Response<?> deleteHistory(@PathVariable Long id) {
		ServiceResult result = historyService.deleteBancowHistory(id);

		if (!result.isResult()) {
			return new Response<>(result, HttpStatus.BAD_REQUEST);
		}
		return new Response<>(result, HttpStatus.OK);
	}

	@PostMapping("/delete")
	public Response<?> deleteHistoryList(@RequestBody HistoryDeleteListDto historyDeleteListDto) {

		ServiceResult result = historyService.deleteHistoryList(historyDeleteListDto);
		if (!result.isResult()) {
			return new Response<>(result, HttpStatus.BAD_REQUEST);
		}
		return new Response<>(result, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public Response<?> updateHistory(@PathVariable Long id, @RequestBody HistoryInputDto historyInputDto) {
		ServiceResult result = historyService.updateHistory(id, historyInputDto);
		if(!result.isResult()){
			return new Response<>(result, HttpStatus.BAD_REQUEST);
		}
		return new Response<>(result, HttpStatus.OK);
	}

}
