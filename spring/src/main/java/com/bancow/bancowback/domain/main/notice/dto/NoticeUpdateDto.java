package com.bancow.bancowback.domain.main.notice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bancow.bancowback.domain.main.notice.entity.NoticeCategory;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class NoticeUpdateDto {

	@NotNull
	private Long id;

	@NotNull
	@JsonProperty("notice_category")
	private NoticeCategory noticeCategory;

	@NotEmpty
	private String title;

	@NotEmpty
	private String message;

	@NotNull
	private boolean status;
}