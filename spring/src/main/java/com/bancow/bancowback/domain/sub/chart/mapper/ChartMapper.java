package com.bancow.bancowback.domain.sub.chart.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bancow.bancowback.domain.sub.chart.dto.KoreanCowResponseDto;
import com.bancow.bancowback.domain.sub.chart.dto.QnaNumResponseDto;
import com.bancow.bancowback.domain.sub.chart.entity.KoreanCow;
import com.bancow.bancowback.domain.sub.chart.entity.KoreanCowCategory;

@Mapper(componentModel = "spring")
public interface ChartMapper {

	ChartMapper INSTANCE = Mappers.getMapper(ChartMapper.class);

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

	default public KoreanCowResponseDto toKoreanCowResponse(KoreanCowCategory category, List<KoreanCow> cowList) {
		List<Map<Integer, Long>> infoList = new ArrayList<>();
		Map<Integer, Long> info = new HashMap<>();
		cowList.forEach(e ->
			info.put(e.getInfoYear(), e.getInfo())
		);
		infoList.add(info);
		return KoreanCowResponseDto.builder()
			.category(category)
			.info(infoList)
			.build();
	}
}
