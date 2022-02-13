package com.bancow.bancowback.domain.main.qna.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QnaDeleteRequestDto {

	private List<Long> id;
}
