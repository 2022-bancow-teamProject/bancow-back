package com.bancow.bancowback.manager.dto;

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
public class ManagerLoginDto {

	@NotBlank(message = "이메일은 필수 입력 사항입니다.")
	private String email;

	@NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
	private String password;
}
