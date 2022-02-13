package com.bancow.bancowback.domain.sub.news.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequestDto {

	@NotEmpty
	private String title;

	@NotEmpty
	private String media;

	@NotEmpty
	private String url;
}
