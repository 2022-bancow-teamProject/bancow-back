package com.bancow.bancowback.domain.main.farmqna.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonProperty("mail_title")
	private String mailTitle;

	@NotBlank
	private String answer;

}
