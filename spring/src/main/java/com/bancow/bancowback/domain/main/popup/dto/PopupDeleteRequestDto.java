package com.bancow.bancowback.domain.main.popup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PopupDeleteRequestDto {

	private List<Long> id;

}
