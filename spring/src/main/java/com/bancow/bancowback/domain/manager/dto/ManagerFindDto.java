package com.bancow.bancowback.domain.manager.dto;

import javax.validation.constraints.Email;
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
public class ManagerFindDto {

	@NotBlank(message = "이름은 필수 항목입니다.")
	private String username;

	@Email(message = "이메일 형식에 맞게 입력해 주세요.")
	@NotBlank(message = "이메일은 필수 항목입니다.")
	private String email;

}
