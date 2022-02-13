package com.bancow.bancowback.domain.main.farmqna.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmQnaAddRequestDto {

	@NotBlank
	@JsonProperty("farm_qna_name")
	private String farmQnaName;

	@NotBlank
	@JsonProperty("phone_number")
	private String phoneNumber;

	@NotBlank
	private String email;

	@NotBlank
	@JsonProperty("farm_name")
	private String farmName;

	@NotBlank
	@JsonProperty("farm_address")
	private String farmAddress;

	@NotNull
	@JsonProperty("cow_num")
	private Integer cowNum;

	@NotBlank
	@JsonProperty("feed_name")
	private String feedName;

	@NotNull
	@JsonProperty("available_date")
	private LocalDate availableDate;
}
