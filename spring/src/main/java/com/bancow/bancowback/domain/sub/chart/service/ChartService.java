package com.bancow.bancowback.domain.sub.chart.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.main.farmqna.service.FarmQnaService;
import com.bancow.bancowback.domain.main.qna.service.QnaService;
import com.bancow.bancowback.domain.sub.chart.dto.QnaNumResponseDto;
import com.bancow.bancowback.domain.sub.chart.mapper.QnaChartMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChartService {

	private final QnaService qnaService;
	private final FarmQnaService farmQnaService;
	private final TokenService tokenService;

	private final QnaChartMapper mapper;

	public QnaNumResponseDto qnaNum(String token) {
		tokenService.validTokenAuthority(token);

		Integer qnaNum = qnaService.countQna();
		Integer uncheckedQna = qnaService.uncheckedQna();

		Integer farmQnaNum = farmQnaService.countFarmQna();
		Integer uncheckedFarmQna = farmQnaService.uncheckedFarmQna();

		return mapper.toResponse(qnaNum, uncheckedQna, farmQnaNum, uncheckedFarmQna);
	}

	public List<Map<String, Object>> farmQnaYear(String token, int year) {
		return makeYearMonthData(token, farmQnaService.countMonth(year));
	}

	public List<Map<String, Object>> qnaYear(String token, int year) {
		return makeYearMonthData(token, qnaService.countMonth(year));
	}

	private List<Map<String, Object>> makeYearMonthData(String token, List<Map<String, Object>> monthData) {
		tokenService.validTokenAuthority(token);
		List<Integer> existMonth = new ArrayList<>();
		for (Map<String, Object> map : monthData) {
			existMonth.add(Integer.parseInt(map.get("YEAR_MONTH").toString().substring(5)));
		}
		String yearInfo = monthData.get(0).get("YEAR_MONTH").toString().substring(0, 5);

		noMonthZero(monthData, existMonth, yearInfo);
		monthData.sort(mapSortComparator());
		return monthData;
	}

	private Comparator<Map<String, Object>> mapSortComparator() {
		return (o1, o2) -> {
			String year_month = (String)o1.get("YEAR_MONTH");
			String year_month2 = (String)o2.get("YEAR_MONTH");

			return year_month.compareTo(year_month2);
		};
	}

	private void noMonthZero(List<Map<String, Object>> res, List<Integer> existMonth, String yearInfo) {
		for (int month = 1; month <= 12; month++) {
			if (!existMonth.contains(month)) {
				res.add(Map.of(
					"MONTH_COUNT", 0,
					"YEAR_MONTH", yearInfo + String.format("%02d", month)
				));
			}
		}
	}

}

