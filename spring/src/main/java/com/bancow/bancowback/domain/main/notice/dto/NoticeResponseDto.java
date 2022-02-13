package com.bancow.bancowback.domain.main.notice.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

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
public class NoticeResponseDto {

	private Long id;

	@JsonProperty("notice_category")
	private NoticeCategory noticeCategory;

	private String title;

	private String message;

	private boolean status;

	@JsonProperty("create_date")
	private LocalDateTime createDate;

	@JsonProperty("update_date")
	private LocalDateTime updateDate;

	@Column(name = "user_name")
	private String username;
}