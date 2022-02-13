package com.bancow.bancowback.domain.main.faq.dto;

import java.time.LocalDateTime;

import com.bancow.bancowback.domain.main.faq.entity.FaqCategory;
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
public class FaqSearchResultDto {

	private Long id;

	@JsonProperty("faq_category")
	private FaqCategory faqCategory;

	private String title;

	private String message;

	private boolean status;

	@JsonProperty("create_date")
	private LocalDateTime createDate;

	@JsonProperty("update_date")
	private LocalDateTime updateDate;
}
