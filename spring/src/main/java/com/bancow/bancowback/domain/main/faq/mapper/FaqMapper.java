package com.bancow.bancowback.domain.main.faq.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.main.faq.dto.FaqAddDto;
import com.bancow.bancowback.domain.main.faq.dto.FaqResponseDto;
import com.bancow.bancowback.domain.main.faq.dto.FaqSearchResultDto;
import com.bancow.bancowback.domain.main.faq.dto.FaqUpdateDto;
import com.bancow.bancowback.domain.main.faq.entity.Faq;
import com.bancow.bancowback.domain.manager.entity.Manager;

@Mapper(componentModel = "spring")
public interface FaqMapper {

	FaqMapper INSTANCE = Mappers.getMapper(
		FaqMapper.class);

	default public Faq toEntity(Manager manager, FaqAddDto faqAddDto) {
		return Faq.builder()
			.faqCategory(faqAddDto.getFaqCategory())
			.title(faqAddDto.getTitle())
			.message(faqAddDto.getMessage())
			.status(faqAddDto.isStatus())
			.createDate(LocalDateTime.now())
			.updateDate(LocalDateTime.now())
			.manager(manager)
			.build();
	}

	default public Faq toAdd(Manager manger, FaqAddDto faqAddDto) {
		return Faq.builder()
			.faqCategory(faqAddDto.getFaqCategory())
			.title(faqAddDto.getTitle())
			.message(faqAddDto.getMessage())
			.status(faqAddDto.isStatus())
			.createDate(LocalDateTime.now())
			.updateDate(LocalDateTime.now())
			.manager(manger)
			.build();
	}

	default public Faq toUpdate(Manager manager, Faq faq, FaqUpdateDto faqUpdateDto) {
		return Faq.builder()
			.id(faqUpdateDto.getId())
			.faqCategory(faqUpdateDto.getFaqCategory())
			.title(faqUpdateDto.getTitle())
			.message(faqUpdateDto.getMessage())
			.status(faqUpdateDto.isStatus())
			.createDate(faq.getCreateDate())
			.updateDate(LocalDateTime.now())
			.manager(manager)
			.build();
	}

	default public FaqResponseDto toResponseDto(Faq faq) {
		return FaqResponseDto.builder()
			.id(faq.getId())
			.faqCategory(faq.getFaqCategory())
			.title(faq.getTitle())
			.message(faq.getMessage())
			.status(faq.isStatus())
			.createDate(LocalDateTime.now())
			.updateDate(LocalDateTime.now())
			.username(faq.getManager().getUsername())
			.build();
	}

	default public FaqSearchResultDto toSearchResultDto(Faq faq) {

		return FaqSearchResultDto.builder()
			.id(faq.getId())
			.faqCategory(faq.getFaqCategory())
			.title(faq.getTitle())
			.message(faq.getMessage())
			.status(faq.isStatus())
			.createDate(faq.getCreateDate())
			.updateDate(faq.getUpdateDate())
			.build();
	}
}