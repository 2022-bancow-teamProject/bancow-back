package com.bancow.bancowback.domain.sub.chart.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bancow.bancowback.TestSupport;
import com.bancow.bancowback.domain.common.util.token.entity.Token;
import com.bancow.bancowback.domain.common.util.token.repository.TokenRepository;
import com.bancow.bancowback.domain.manager.entity.Manager;

class ChartControllerTest extends TestSupport {

	@Autowired
	private TokenRepository tokenRepository;

	@Test
	@Transactional
	void qnaNum() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
			get("/api/chart/qna")
				.header("TOKEN", token.getToken())
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.qna_num").description("총 QnA 갯수"),
						fieldWithPath("data.unchecked_qna_num").description("아직 확인하지 않은 QnA 갯수"),
						fieldWithPath("data.farm_qna_num").description("총 농가입점 문의 갯수"),
						fieldWithPath("data.unchecked_farm_qna_num").description("아직 확인하지 않은 농가입점 문의 갯수"),
						fieldWithPath("status").description("HTTP Status"))
				)
			)
		;
	}

	@Test
	@Transactional
	void farmQnaYear() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
			get("/api/chart/farmqna/{year}", 2020)
				.header("TOKEN", token.getToken())
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					pathParameters(
						parameterWithName("year").description("보고 싶은 차트의 연도")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data[0].month_count").description("해당 연월의 농가 입점 문의 갯수"),
						fieldWithPath("data[0].ym").description("해당 연월"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void qnaYear() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
			get("/api/chart/qna/{year}", 2021)
				.header("TOKEN", token.getToken())
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					pathParameters(
						parameterWithName("year").description("보고 싶은 차트의 연도")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data[0].month_count").description("해당 연월의 농가 입점 문의 갯수"),
						fieldWithPath("data[0].ym").description("해당 연월"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	public void koreanCow() throws Exception {
		mockMvc.perform(
			get("/api/chart/koreancow/{category}", "AUCTION_PRICE")
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					pathParameters(
						parameterWithName("category").description("한우 차트 카테고리 (AUCTION_PRICE, BEEF_PRICE, CONSUME_KG) 중 선택")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.category").description("한우 차트 카테고리"),
						fieldWithPath("data.info").description("연도별 결과 데이터"),
						fieldWithPath("data.info[0].2011").description("2011년 데이터"),
						fieldWithPath("data.info[0].2012").description("2012년 데이터"),
						fieldWithPath("data.info[0].2013").description("2013년 데이터"),
						fieldWithPath("data.info[0].2014").description("2014년 데이터"),
						fieldWithPath("data.info[0].2015").description("2015년 데이터"),
						fieldWithPath("data.info[0].2016").description("2016년 데이터"),
						fieldWithPath("data.info[0].2017").description("2017년 데이터"),
						fieldWithPath("data.info[0].2018").description("2018년 데이터"),
						fieldWithPath("data.info[0].2019").description("2019년 데이터"),
						fieldWithPath("data.info[0].2020").description("2020년 데이터"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}
}