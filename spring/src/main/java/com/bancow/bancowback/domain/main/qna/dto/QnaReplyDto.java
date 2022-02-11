package com.bancow.bancowback.domain.main.qna.dto;

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
public class QnaReplyDto {

	@NotBlank
	@JsonProperty("mail_title")
	private String mailTitle;

	@NotBlank
	private String answer;

}
