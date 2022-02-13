package com.bancow.bancowback.domain.main.farmqna.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class FarmQnaResponseDto {

	private Long id;

	@JsonProperty("farm_qna_name")
	private String farmQnaName;

	@JsonProperty("phone_number")
	private String phoneNumber;

	private String email;

	@JsonProperty("farm_name")
	private String farmName;

	@JsonProperty("farm_address")
	private String farmAddress;

	@JsonProperty("cow_num")
	private Integer cowNum;

	@JsonProperty("feed_name")
	private String feedName;

	private Boolean checked;

	@JsonProperty("available_date")
	private LocalDate availableDate;

	@JsonProperty("create_date")
	private LocalDateTime createDate;
}
