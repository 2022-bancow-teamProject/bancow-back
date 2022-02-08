package com.bancow.bancowback.domain.main.history.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class HistoryInputDto {

	@NotBlank(message = "날짜를 입력해주세요.")
	private String historyDate;

	@NotBlank(message = "내용을 입력해주세요.")
	private String message;

	@NotBlank(message = "공개여부를 입력해주세요.")
	private boolean status;

	private String userName;
}
