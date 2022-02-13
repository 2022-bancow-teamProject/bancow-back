package com.bancow.bancowback.domain.manager.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.common.util.PasswordUtils;
import com.bancow.bancowback.domain.manager.dto.ManagerRegisterDto;
import com.bancow.bancowback.domain.manager.dto.ManagerResponseDto;
import com.bancow.bancowback.domain.manager.entity.Manager;
import com.bancow.bancowback.domain.manager.entity.ManagerStatus;

@Mapper(componentModel = "spring")
public interface ManagerMapper {

	ManagerMapper INSTANCE = Mappers.getMapper(ManagerMapper.class);

	default public Manager registerToEntity(ManagerRegisterDto dto) {
		String encryptedPassword = PasswordUtils.encryptedPassword(dto.getPassword());
		return Manager.builder()
			.email(dto.getEmail())
			.password(encryptedPassword)
			.username(dto.getUsername())
			.managerStatus(ManagerStatus.PENDING_EMAIL)
			.createDate(LocalDateTime.now())
			.updateDate(LocalDateTime.now())
			.build();
	}

	default public ManagerResponseDto toRequest(Manager manager) {
		return ManagerResponseDto.builder()
			.id(manager.getId())
			.email(manager.getEmail())
			.username(manager.getUsername())
			.managerStatus(manager.getManagerStatus())
			.createDate(manager.getCreateDate())
			.updateDate(manager.getCreateDate())
			.build();
	}
}
