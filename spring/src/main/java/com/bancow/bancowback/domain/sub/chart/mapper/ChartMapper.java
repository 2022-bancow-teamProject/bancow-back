package com.bancow.bancowback.domain.sub.chart.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
		Map<Integer, Long> sortInfo = sortMapByKey(info);
		infoList.add(sortInfo);
		return KoreanCowResponseDto.builder()
			.category(category)
			.info(infoList)
			.build();
	}

	default public HashMap<Integer, Long> sortMapByKey(Map<Integer, Long> map) {
		List<Map.Entry<Integer, Long>> entries = new LinkedList<>(map.entrySet());
		entries.sort((o1, o2) -> o1.getKey().compareTo(o2.getKey()));

		LinkedHashMap<Integer, Long> result = new LinkedHashMap<>();
		for (Map.Entry<Integer, Long> entry : entries) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
