package com.bancow.bancowback.domain.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ManagerLoginResultDto {
	private String token;
	private final String message = "로그인에 성공하였습니다.";
}
