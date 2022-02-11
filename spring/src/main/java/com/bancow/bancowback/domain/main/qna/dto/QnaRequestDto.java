package com.bancow.bancowback.domain.main.qna.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bancow.bancowback.domain.main.qna.entity.QnaCategory;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QnaRequestDto {

	@NotNull
	private QnaCategory category;

	@NotEmpty
	private String phoneNumber;

	@NotEmpty
	@JsonProperty("qna_name")
	private String qnaName;

	@Email
	@NotEmpty
	private String email;

	@NotEmpty
	private String title;

	@NotEmpty
	private String message;
}
