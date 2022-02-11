package com.bancow.bancowback.domain.sub.chart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QnaNumResponseDto {
	@JsonProperty("qna_num")
	private Integer qnaNum;

	@JsonProperty("unchecked_qna_num")
	private Integer uncheckedQna;

	@JsonProperty("farm_qna_num")
	private Integer farmQnaNum;

	@JsonProperty("unchecked_farm_qna_num")
	private Integer uncheckedFarmQna;

}
