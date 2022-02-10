package com.bancow.bancowback.domain.main.farmqna.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmQnaDeleteRequestDto {

	private List<Long> id;
}
