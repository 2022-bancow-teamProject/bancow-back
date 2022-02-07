package com.bancow.bancowback.domain.main.popup.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.bancow.bancowback.TestSupport;
import com.bancow.bancowback.domain.common.util.token.entity.Token;
import com.bancow.bancowback.domain.common.util.token.repository.TokenRepository;
import com.bancow.bancowback.domain.manager.entity.Manager;

class PopupControllerTest extends TestSupport {

	@Autowired
	private TokenRepository tokenRepository;

	@Test
	@Transactional
	void addPopup() throws Exception {

		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		MockMultipartFile popup_image = new MockMultipartFile("popup_image", "뱅 카 우.png", "image/png",
			readImage("/img/뱅 카 우.png").getBytes());
		MockMultipartFile popup_request = new MockMultipartFile("popup_request", "req.json", "application/json",
			readJson("/json/req.json").getBytes());

		mockMvc.perform(multipart("/api/popup/add")
				.file(popup_image)
				.file(popup_request)
				.header("TOKEN", tokenAdmin.getToken())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestParts(
					partWithName("popup_image").description("이미지 파일"),
					partWithName("popup_request").description("Json 파일")),
				requestPartBody("popup_request"),
				requestPartFields("popup_request",
					fieldWithPath("title").description("팝업 제목"),
					fieldWithPath("start_date").description("팝업 게시 시작 날짜"),
					fieldWithPath("end_date").description("팝업 게시 마감 날짜")
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
	void getPopupDetail() throws Exception {
		Long id = 1L;
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		mockMvc.perform(
				get("/api/popup/{id}", id)
					.header("TOKEN", tokenAdmin.getToken())

			).andExpect(status().isOk())
			.andDo(
				restDocs.document(

					pathParameters(
						parameterWithName("id").description("팝업 번호")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.id").description("팝업 번호"),
						fieldWithPath("data.title").description("팝업 제목"),
						fieldWithPath("data.image").description("팝업 이미지"),
						fieldWithPath("data.status").description("팝업 사용 유무"),
						fieldWithPath("data.username").description("팝업 등록한 관리자"),
						fieldWithPath("data.start_date").description("팝업 게시 시작 날짜"),
						fieldWithPath("data.end_date").description("팝업 게시 마감 날짜"),
						fieldWithPath("data.create_date").description("팝업 생성 시간"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void getPopupPaging() throws Exception {
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		mockMvc.perform(
				get("/api/popup")
					.param("page", "0")
					.header("TOKEN", tokenAdmin.getToken())
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("로그인한 SUPER 계정의 토큰값")
					),
					requestParameters(
						parameterWithName("page").description("페이징 처리를 위한 페이지 값 (0부터 시작)")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.content").description("모든 Manager의 정보"),
						fieldWithPath("data.content[0].id").description("Manager id (pk)"),
						fieldWithPath("data.content[0].title").description("이메일"),
						fieldWithPath("data.content[0].image").description("팝업 이미지"),
						fieldWithPath("data.content[0].status").description("팝업 게시여부"),
						fieldWithPath("data.content[0].username").description("팝업 생성한 관리자"),
						fieldWithPath("data.content[0].start_date").description("팝업 게시 시작날"),
						fieldWithPath("data.content[0].end_date").description("팝업 게시 마감날"),
						fieldWithPath("data.content[0].create_date").description("팝업 생성시간"),
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