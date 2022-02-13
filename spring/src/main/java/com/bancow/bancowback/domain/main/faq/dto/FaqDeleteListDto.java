package com.bancow.bancowback.domain.main.faq.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqDeleteListDto {

	@JsonProperty("id_list")
	private List<Long> idList;
}
