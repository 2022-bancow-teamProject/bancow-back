package com.bancow.bancowback.domain.sub.chart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.sub.chart.dto.QnaNumResponseDto;

@Mapper(componentModel = "spring")
public interface QnaChartMapper {

	QnaChartMapper INSTANCE = Mappers.getMapper(QnaChartMapper.class);

	default public QnaNumResponseDto toResponse(
		Integer qnaNum,
		Integer uncheckedQna,
		Integer farmQnaNum,
		Integer uncheckedFarmQna
	) {
		return QnaNumResponseDto.builder()
			.qnaNum(qnaNum)
			.uncheckedQna(uncheckedQna)
			.farmQnaNum(farmQnaNum)
			.uncheckedFarmQna(uncheckedFarmQna)
			.build();
	}
}
