package com.bancow.bancowback.domain.sub.farm.mapper;

import org.springframework.stereotype.Component;

import com.bancow.bancowback.domain.manager.entity.Manager;
import com.bancow.bancowback.domain.sub.farm.dto.FarmAddRequestDto;
import com.bancow.bancowback.domain.sub.farm.entity.Farm;
import com.bancow.bancowback.domain.sub.farm.entity.FarmInfo;

@Component
public class FarmMapper {

	public FarmAddRequestDto toFarmAddRequestDto(FarmAddRequestDto farmAddRequestDto,
		String farmImageUploadPath, String farmCEOImageUploadPath) {
		return FarmAddRequestDto.builder()
			.farmName(farmAddRequestDto.getFarmName())
			.ceoName(farmAddRequestDto.getCeoName())
			.title(farmAddRequestDto.getTitle())
			.content(farmAddRequestDto.getContent())
			.farmImageUploadPath(farmImageUploadPath)
			.farmCEOImageUploadPath(farmCEOImageUploadPath)
			.build();
	}

	public FarmInfo<FarmAddRequestDto> toFarmInfo(Manager manager, FarmAddRequestDto farmAddRequestDto) {
		return FarmInfo.<FarmAddRequestDto>builder()
			.manager(manager)
			.Dto(farmAddRequestDto)
			.build();
	}

	public Farm toEntity(Manager manager, FarmAddRequestDto farmAddRequestDto) {
		return Farm.builder()
			.farmName(farmAddRequestDto.getFarmName())
			.ceoName(farmAddRequestDto.getCeoName())
			.title(farmAddRequestDto.getTitle())
			.content(farmAddRequestDto.getContent())
			.farmImage(farmAddRequestDto.getFarmImageUploadPath())
			.farmCEOImage(farmAddRequestDto.getFarmCEOImageUploadPath())
			.status(Boolean.FALSE)
			.manager(manager)
			.build();
	}
}
