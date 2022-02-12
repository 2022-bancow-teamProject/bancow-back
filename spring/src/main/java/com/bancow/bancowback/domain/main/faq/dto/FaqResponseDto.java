package com.bancow.bancowback.domain.main.faq.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import com.bancow.bancowback.domain.main.faq.entity.FaqCategory;

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
public class FaqResponseDto {

	private Long id;

	@Column(name = "faq_category", nullable = false)
	private FaqCategory faqCategory;

	private String title;

	private String message;

	private boolean status;

	private LocalDateTime createDate;

	private LocalDateTime updateDate;

	@Column(name = "user_name")
	private String username;
}