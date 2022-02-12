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
		tokenService.validTokenAuthority(token);
		List<Map<String, Object>> res = farmQnaService.countMonth(year);

		List<Integer> existMonth = new ArrayList<>();
		for (Map<String, Object> map : res) {
			existMonth.add(Integer.parseInt(map.get("YEAR_MONTH").toString().substring(5)));
		}
		String yearInfo = res.get(0).get("YEAR_MONTH").toString().substring(0, 5);
		existMonth.forEach(System.out::println);

		noMonthZero(res, existMonth, yearInfo);

		res.sort(mapSortComparator());
		return res;
	}

	private Comparator<Map<String, Object>> mapSortComparator() {
		return new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				String year_month = (String)o1.get("YEAR_MONTH");
				String year_month2 = (String)o2.get("YEAR_MONTH");

				return year_month.compareTo(year_month2);
			}
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

