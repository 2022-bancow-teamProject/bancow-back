package com.bancow.bancowback.domain.main.notice.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bancow.bancowback.domain.main.notice.entity.NoticeCategory;
import com.bancow.bancowback.domain.manager.entity.Manager;

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

	@Column(name = "notice_category", nullable = false)
	private NoticeCategory noticeCategory;

	private String title;

	private String message;

	private boolean status;

	private LocalDateTime createDate;

	private LocalDateTime updateDate;

	@Column(name = "user_name")
	private String username;
}