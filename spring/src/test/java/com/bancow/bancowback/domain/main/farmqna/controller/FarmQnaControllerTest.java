package com.bancow.bancowback.domain.main.farmqna.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import com.bancow.bancowback.TestSupport;
import com.bancow.bancowback.domain.common.util.token.entity.Token;
import com.bancow.bancowback.domain.common.util.token.repository.TokenRepository;
import com.bancow.bancowback.domain.manager.entity.Manager;

class FarmQnaControllerTest extends TestSupport {

	@Autowired
	private TokenRepository tokenRepository;

	@Test
	@Transactional
	void addFarmQna() throws Exception {
		mockMvc.perform(
			post("/api/farmqna/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n"
					+ "  \"name\": \"김철수\",\n"
					+ "  \"phoneNumber\": \"010-3991-7102\",\n"
					+ "  \"email\": \"gmldnr2222@naver.com\",\n"
					+ "  \"farmName\": \"속초농장\",\n"
					+ "  \"farmAddress\": \"강원도 속초시 교동 밤골3길\",\n"
					+ "  \"cowNum\": \"100\",\n"
					+ "  \"feedName\": \"먹이이름입니다.\",\n"
					+ "  \"availableDate\": \"2022-02-20\"\n"
					+ "}")
				.accept(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestFields(
						fieldWithPath("name").description("이름"),
						fieldWithPath("phoneNumber").description("전화 번호"),
						fieldWithPath("email").description("이메일"),
						fieldWithPath("farmName").description("농가 이름"),
						fieldWithPath("farmAddress").description("농가 주소"),
						fieldWithPath("cowNum").description("사육 두수"),
						fieldWithPath("feedName").description("사용 사료"),
						fieldWithPath("availableDate").description("실사 가능 일자")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("성공 여부"),
						fieldWithPath("data.message").description("결과 데이터"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void getFarmQna() throws Exception {

		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();

		mockMvc.perform(
			get("/api/farmqna/{id}", 1)
				.header("TOKEN", token.getToken())
				.accept(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					pathParameters(
						parameterWithName("id").description("농가 입점 문의 게시글의 id")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.id").description("아이디"),
						fieldWithPath("data.name").description("이름"),
						fieldWithPath("data.phoneNumber").description("전화번호"),
						fieldWithPath("data.email").description("이메일"),
						fieldWithPath("data.farmName").description("농가 이름"),
						fieldWithPath("data.id").description("아이디"),
						fieldWithPath("data.farmAddress").description("농가 주소"),
						fieldWithPath("data.cowNum").description("사육 두수"),
						fieldWithPath("data.feedName").description("사용사료"),
						fieldWithPath("data.checked").description("답변여부"),
						fieldWithPath("data.availableDate").description("실사 가능 일자"),
						fieldWithPath("data.createDate").description("문의 날짜"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void getFarmQnaPaging() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();

		mockMvc.perform(
			get("/api/farmqna")
				.param("page", "0")
				.header("TOKEN", token.getToken())
				.accept(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					requestParameters(
						parameterWithName("page").description("페이지 번호 (0부터 시작)")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.content").description("농가 입점 문의 정보"),
						fieldWithPath("data.content[0].id").description("아이디"),
						fieldWithPath("data.content[0].name").description("이름"),
						fieldWithPath("data.content[0].phoneNumber").description("전화번호"),
						fieldWithPath("data.content[0].email").description("이메일"),
						fieldWithPath("data.content[0].farmName").description("농가 이름"),
						fieldWithPath("data.content[0].id").description("아이디"),
						fieldWithPath("data.content[0].farmAddress").description("농가 주소"),
						fieldWithPath("data.content[0].cowNum").description("사육 두수"),
						fieldWithPath("data.content[0].feedName").description("사용사료"),
						fieldWithPath("data.content[0].checked").description("답변여부"),
						fieldWithPath("data.content[0].availableDate").description("실사 가능 일자"),
						fieldWithPath("data.content[0].createDate").description("문의 날짜"),
						fieldWithPath("data.pageable").description("Pageable 설명"),
						fieldWithPath("data.pageable.sort").description("페이지 정렬 설명"),
						fieldWithPath("data.pageable.sort.empty").description("비어있는 지 여부"),
						fieldWithPath("data.pageable.sort.unsorted").description("비정렬 여부"),
						fieldWithPath("data.pageable.sort.sorted").description("정렬 여부"),
						fieldWithPath("data.pageable.offset").description("Skip할 데이터 양"),
						fieldWithPath("data.pageable.pageNumber").description("페이지 번호"),
						fieldWithPath("data.pageable.pageSize").description("페이지 크기"),
						fieldWithPath("data.pageable.paged").description("페이지 수를 매긴 여부"),
						fieldWithPath("data.pageable.unpaged").description("페이지 수를 매기지 않은 여부"),
						fieldWithPath("data.totalPages").description("총 페이지"),
						fieldWithPath("data.totalElements").description("모든 데이터 개수"),
						fieldWithPath("data.last").description("마지막 페이지 여부"),
						fieldWithPath("data.size").description("한 페이지에서 보여줄 데이터의 개수"),
						fieldWithPath("data.numberOfElements").description("모든 데이터 개수"),
						fieldWithPath("data.number").description("페이지 번호"),
						fieldWithPath("data.sort").description("data 정렬 설명"),
						fieldWithPath("data.sort.empty").description("비어있는 지 여부"),
						fieldWithPath("data.sort.unsorted").description("비정렬 여부"),
						fieldWithPath("data.sort.sorted").description("정렬 여부"),
						fieldWithPath("data.first").description("첫번째 페이지 여부"),
						fieldWithPath("data.empty").description("비어있는 지 여부"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	public void deleteFarmQna() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
			delete("/api/farmqna/{id}", 1)
				.header("TOKEN", token.getToken())
				.accept(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					pathParameters(
						parameterWithName("id").description("농가 입점 문의 게시글의 id")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("농가입점 문의 삭제여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status"))
				)
			)
		;
	}

}