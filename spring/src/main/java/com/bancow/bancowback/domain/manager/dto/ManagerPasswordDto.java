package com.bancow.bancowback.domain.manager.dto;

import javax.validation.constraints.NotBlank;

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
public class ManagerPasswordDto {

	@NotBlank(message = "비밀번호1은 필수 항목입니다.")
	private String password1;

	@NotBlank(message = "비밀번호2는 필수 항목입니다.")
	private String password2;
}
