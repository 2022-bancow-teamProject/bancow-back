package com.bancow.bancowback.domain.sub.farm.mapper;

import org.springframework.stereotype.Component;

import com.bancow.bancowback.domain.manager.entity.Manager;
import com.bancow.bancowback.domain.sub.farm.dto.FarmAddRequestDto;
import com.bancow.bancowback.domain.sub.farm.dto.FarmDetailResponseDto;
import com.bancow.bancowback.domain.sub.farm.dto.FarmDistributeResponseDto;
import com.bancow.bancowback.domain.sub.farm.dto.FarmPagingResponseDto;
import com.bancow.bancowback.domain.sub.farm.dto.FarmUpdateRequestDto;
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

	public FarmInfo<FarmUpdateRequestDto> toUpdateFarmInfo(Manager manager, FarmUpdateRequestDto farmUpdateRequestDto) {
		return FarmInfo.<FarmUpdateRequestDto>builder()
			.manager(manager)
			.Dto(farmUpdateRequestDto)
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
			.content(farm.getContent())
			.farmCEOImage("https://kr.object.ncloudstorage.com/bancowback/" + farm.getFarmCEOImage())
			.build();
	}

	public FarmPagingResponseDto toFarmPagingResponseDto(Farm farm) {
		return FarmPagingResponseDto.builder()
			.id(farm.getId())
			.title(farm.getTitle())
			.farmName(farm.getFarmName())
			.ceoName(farm.getCeoName())
			.userName(farm.getManager().getUsername())
			.status(farm.getStatus())
			.create_date(farm.getCreateDate())
			.build();
	}

	public FarmDetailResponseDto toFarmDetailResponseDto(Farm farm) {
		return FarmDetailResponseDto.builder()
			.id(farm.getId())
			.title(farm.getTitle())
			.content(farm.getContent())
			.farmName(farm.getFarmName())
			.ceoName(farm.getCeoName())
			.userName(farm.getManager().getUsername())
			.farmImage("https://kr.object.ncloudstorage.com/bancowback/" + farm.getFarmImage())
			.farmCEOImage("https://kr.object.ncloudstorage.com/bancowback/" + farm.getFarmCEOImage())
			.status(farm.getStatus())
			.create_date(farm.getCreateDate())
			.build();
	}

	public FarmUpdateRequestDto toFarmUpdateRequestDto(FarmUpdateRequestDto farmUpdateRequestDto,
		String farmImageUploadPath, String farmCEOImageUploadPath) {
		return FarmUpdateRequestDto.builder()
			.id(farmUpdateRequestDto.getId())
			.farmName(farmUpdateRequestDto.getFarmName())
			.ceoName(farmUpdateRequestDto.getCeoName())
			.title(farmUpdateRequestDto.getTitle())
			.content(farmUpdateRequestDto.getContent())
			.farmImageUploadPath(farmImageUploadPath)
			.farmCEOImageUploadPath(farmCEOImageUploadPath)
			.status(farmUpdateRequestDto.getStatus())
			.build();
	}

	public Farm toUpdateEntity(Manager manager, Farm farm, FarmUpdateRequestDto farmUpdateRequestDto) {
		return Farm.builder()
			.id(farmUpdateRequestDto.getId())
			.farmName(farmUpdateRequestDto.getFarmName())
			.ceoName(farmUpdateRequestDto.getCeoName())
			.title(farmUpdateRequestDto.getTitle())
			.content(farmUpdateRequestDto.getContent())
			.farmImage(farmUpdateRequestDto.getFarmImageUploadPath())
			.farmCEOImage(farmUpdateRequestDto.getFarmCEOImageUploadPath())
			.status(farmUpdateRequestDto.getStatus())
			.createDate(farm.getCreateDate())
			.manager(manager)
			.build();
	}

	public Farm toUpdateNotImageEntity(Manager manager, Farm farm, FarmUpdateRequestDto farmUpdateRequestDto) {
		return Farm.builder()
			.id(farmUpdateRequestDto.getId())
			.farmName(farmUpdateRequestDto.getFarmName())
			.ceoName(farmUpdateRequestDto.getCeoName())
			.title(farmUpdateRequestDto.getTitle())
			.content(farmUpdateRequestDto.getContent())
			.farmImage(farm.getFarmImage())
			.farmCEOImage(farm.getFarmCEOImage())
			.status(farmUpdateRequestDto.getStatus())
			.createDate(farm.getCreateDate())
			.manager(manager)
			.build();
	}
}
