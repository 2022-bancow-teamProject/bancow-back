package com.bancow.bancowback.domain.main.history.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancow.bancowback.domain.common.dto.Response;
import com.bancow.bancowback.domain.main.history.dto.HistoryAddDto;
import com.bancow.bancowback.domain.main.history.dto.HistoryDeleteListDto;
import com.bancow.bancowback.domain.main.history.dto.HistoryUpdateDto;
import com.bancow.bancowback.domain.main.history.service.HistoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/history")
public class HistoryController {

	private final HistoryService historyService;

	@GetMapping("/public/{id}")
	public Response<?> getPublicHistory(@PathVariable Long id){
		return new Response<>(historyService.getPublicHistory(id), HttpStatus.OK);
	}

	@GetMapping("/public")
	public Response<?> getPublicHistoryPaging(@RequestParam int page){
		return new Response<>(historyService.getPublicHistoryPaging(page), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Response<?> getHistory(@RequestHeader("TOKEN") String token, @PathVariable Long id) {
		return new Response<>(historyService.getHistory(token, id), HttpStatus.OK);
	}

	@GetMapping
	public Response<?> getHistoryPaging(@RequestHeader("TOKEN") String token, @RequestParam int page) {
		return new Response<>(historyService.getHistoryPaging(token, page), HttpStatus.OK);
	}

	@PostMapping("/add")
	public Response<?> addHistory(@RequestHeader("TOKEN") String token, @RequestBody @Valid HistoryAddDto historyAddDto) {
		return new Response<>(historyService.addHistory(token, historyAddDto), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public Response<?> deleteHistory(@RequestHeader("TOKEN") String token, @PathVariable Long id) {
		return new Response<>(historyService.deleteHistory(token, id), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public Response<?> deleteHistoryList(@RequestHeader("TOKEN") String token, @RequestBody HistoryDeleteListDto historyDeleteListDto){
		return new Response<>(historyService.deleteHistoryList(token, historyDeleteListDto.getIdList()), HttpStatus.OK);
	}

	@PatchMapping("/update")
	public Response<?> updateHistory(@RequestHeader("TOKEN") String token, @RequestBody HistoryUpdateDto historyUpdateDto){

		return new Response<>(historyService.updateHistory(token, historyUpdateDto), HttpStatus.OK);
	}

}
