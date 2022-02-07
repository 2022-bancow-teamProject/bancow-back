package com.bancow.bancowback.domain.main.notice.dto;

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
public class NoticeInputDto {

	@NotBlank(message = "카테고리를 선택해주세요.")
	private String noticeCategory;

	private String username;

	@NotBlank(message = "제목을 입력해주세요.")
	private String title;

	@NotBlank(message = "내용을 입력해주세요.")
	private String message;

	@NotBlank(message = "공개여부를 선택해주세요.")
	private boolean status;

}