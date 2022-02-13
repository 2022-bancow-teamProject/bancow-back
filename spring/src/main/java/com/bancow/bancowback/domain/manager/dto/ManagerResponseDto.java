package com.bancow.bancowback.domain.manager.dto;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.bancow.bancowback.domain.manager.entity.ManagerStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerResponseDto {

	private Long id;

	private String email;

	private String username;

	@Enumerated(EnumType.STRING)
	private ManagerStatus managerStatus;

	@JsonProperty("create_date")
	private LocalDateTime createDate;

	@JsonProperty("update_date")
	private LocalDateTime updateDate;
}
