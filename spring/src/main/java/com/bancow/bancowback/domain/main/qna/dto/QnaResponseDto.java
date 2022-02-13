package com.bancow.bancowback.domain.main.qna.dto;

import java.time.LocalDateTime;

import com.bancow.bancowback.domain.main.qna.entity.QnaCategory;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QnaResponseDto {
	private Long id;

	private QnaCategory category;

	@JsonProperty("qna_name")
	private String qnaName;

	@JsonProperty("phone_number")
	private String phoneNumber;

	private String email;

	private String title;

	private String message;

	private boolean checked;

	@JsonProperty("create_date")
	private LocalDateTime createDate;
}
