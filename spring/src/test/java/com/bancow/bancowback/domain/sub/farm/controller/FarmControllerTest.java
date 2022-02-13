package com.bancow.bancowback.domain.sub.farm.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.bancow.bancowback.TestSupport;
import com.bancow.bancowback.domain.common.util.token.entity.Token;
import com.bancow.bancowback.domain.common.util.token.repository.TokenRepository;
import com.bancow.bancowback.domain.manager.entity.Manager;

public class FarmControllerTest extends TestSupport {

	@Autowired
	private TokenRepository tokenRepository;

	@Test
	@Transactional
	void addFarm() throws Exception {
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		MockMultipartFile farm_image = new MockMultipartFile("farm_image", "bancow.png", "image/png",
			readImage("/img/bancow.png").getBytes());
		MockMultipartFile farm_ceo_image = new MockMultipartFile("farm_ceo_image", "bancow.png", "image/png",
			readImage("/img/bancow.png").getBytes());
		MockMultipartFile farm_request = new MockMultipartFile("farm_request", "req.json", "application/json",
			readJson("/json/farm/add.json").getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/farm/add")
				.file(farm_image)
				.file(farm_ceo_image)
				.file(farm_request)
				.header("TOKEN", tokenAdmin.getToken())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestHeaders(
					headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
				),
				requestParts(
					partWithName("farm_image").description("농장 이미지 파일"),
					partWithName("farm_ceo_image").description("농장 대표님 이미지 파일"),
					partWithName("farm_request").description("Json 파일")),
				requestPartBody("farm_request"),
				requestPartFields("farm_request",
					fieldWithPath("farm_name").description("농장 이름"),
					fieldWithPath("ceo_name").description("대표님 이름"),
					fieldWithPath("title").description("제목"),
					fieldWithPath("content").description("제목")
				),
				responseFields(
					fieldWithPath("data").description("결과 데이터"),
					fieldWithPath("data.result").description("결과"),
					fieldWithPath("data.message").description("메시지"),
					fieldWithPath("status").description("HTTP Status")
				)
			));
	}

	@Test
	@Transactional
	void getFarmDistribute() throws Exception {
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		mockMvc.perform(
				get("/api/farm/distribute")

			).andExpect(status().isOk())
			.andDo(
				restDocs.document(
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data[0].id").description("id"),
						fieldWithPath("data[0].title").description("제목"),
						fieldWithPath("data[0].farm_name").description("농장 이름"),
						fieldWithPath("data[0].ceo_name").description("농장 대표님"),
						fieldWithPath("data[0].farm_ceo_image").description("대표님 사진"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void getFarmDetail() throws Exception {
		Long id = 1L;
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		mockMvc.perform(
				get("/api/farm/{id}", id)
					.header("TOKEN", tokenAdmin.getToken())

			).andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					pathParameters(
						parameterWithName("id").description("번호")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.id").description("id (pk)"),
						fieldWithPath("data.title").description("제목"),
						fieldWithPath("data.farm_name").description("농장 이름"),
						fieldWithPath("data.ceo_name").description("대표님 이름"),
						fieldWithPath("data.ceo_name").description("대표님 이름"),
						fieldWithPath("data.farm_image").description("농장 사진"),
						fieldWithPath("data.farm_ceo_image").description("대표님 사진"),
						fieldWithPath("data.status").description("게시여부"),
						fieldWithPath("data.user_name").description("생성한 관리자"),
						fieldWithPath("data.create_date").description("생성시간"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void getFarmPaging() throws Exception {
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		mockMvc.perform(
				get("/api/farm")
					.param("page", "0")
					.header("TOKEN", tokenAdmin.getToken())
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					requestParameters(
						parameterWithName("page").description("페이징 처리를 위한 페이지 값 (0부터 시작)")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.content").description("정보"),
						fieldWithPath("data.content[0].id").description("id (pk)"),
						fieldWithPath("data.content[0].title").description("제목"),
						fieldWithPath("data.content[0].farm_name").description("농장 이름"),
						fieldWithPath("data.content[0].ceo_name").description("대표님 이름"),
						fieldWithPath("data.content[0].status").description("게시여부"),
						fieldWithPath("data.content[0].user_name").description("생성한 관리자"),
						fieldWithPath("data.content[0].create_date").description("생성시간"),
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
			);

	}
}
