package com.bancow.bancowback.domain.main.faq.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class FaqAddDto {

	@NotNull
	@JsonProperty("faq_category")
	private FaqCategory faqCategory;

	@NotEmpty
	private String title;

	@NotEmpty
	private String message;

	@NotNull
	private boolean status;
}