package com.bancow.bancowback.domain.sub.farm.mapper;

import org.springframework.stereotype.Component;

import com.bancow.bancowback.domain.manager.entity.Manager;
import com.bancow.bancowback.domain.sub.farm.dto.FarmAddRequestDto;
import com.bancow.bancowback.domain.sub.farm.dto.FarmDistributeResponseDto;
import com.bancow.bancowback.domain.sub.farm.dto.FarmResponseDto;
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

	public FarmDistributeResponseDto toDistributeResponseDto(Farm farm) {
		return FarmDistributeResponseDto.builder()
			.id(farm.getId())
			.farmName(farm.getFarmName())
			.ceoName(farm.getCeoName())
			.title(farm.getTitle())
			.farmCEOImage("https://kr.object.ncloudstorage.com/bancowback/" + farm.getFarmCEOImage())
			.build();
	}

	public FarmResponseDto toResponseDto(Farm farm) {
		return FarmResponseDto.builder()
			.id(farm.getId())
			.title(farm.getTitle())
			.farmName(farm.getFarmName())
			.ceoName(farm.getCeoName())
			.userName(farm.getManager().getUsername())
			.status(farm.getStatus())
			.create_date(farm.getCreateDate())
			.build();
	}
}
