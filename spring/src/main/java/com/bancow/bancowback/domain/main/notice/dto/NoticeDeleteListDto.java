package com.bancow.bancowback.domain.main.notice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDeleteListDto {

	@JsonProperty("id_list")
	private List<Long> idList;
}
