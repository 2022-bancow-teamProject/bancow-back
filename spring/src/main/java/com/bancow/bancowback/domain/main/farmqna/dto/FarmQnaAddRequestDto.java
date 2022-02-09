package com.bancow.bancowback.domain.main.farmqna.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	private String name;

	@NotBlank
	private String phoneNumber;

	@NotBlank
	private String email;

	@NotBlank
	private String farmName;

	@NotBlank
	private String farmAddress;

	@NotNull
	private Integer cowNum;

	@NotBlank
	private String feedName;

	@NotNull
	private LocalDate availableDate;
}
