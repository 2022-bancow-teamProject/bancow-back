package com.bancow.bancowback.domain.manager.dto;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.bancow.bancowback.domain.manager.entity.ManagerStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerRequestDto {

	private Long id;

	private String email;

	private String username;

	@Enumerated(EnumType.STRING)
	private ManagerStatus managerStatus;

	private LocalDateTime createDate;

	private LocalDateTime updateDate;
}
