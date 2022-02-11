package com.bancow.bancowback.domain.main.farmqna.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmQnaReplyDto {

	@NotBlank
	private String mailTitle;

	@NotBlank
	private String answer;

}
