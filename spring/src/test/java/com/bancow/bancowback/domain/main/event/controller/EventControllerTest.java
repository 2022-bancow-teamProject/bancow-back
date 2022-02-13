package com.bancow.bancowback.domain.main.event.controller;

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

class EventControllerTest extends TestSupport {
	@Autowired
	private TokenRepository tokenRepository;

	@Test
	@Transactional
	void addEvent() throws Exception {

		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		MockMultipartFile event_image = new MockMultipartFile("event_image", "bancow.png", "image/png",
			readImage("/img/bancow.png").getBytes());
		MockMultipartFile event_request = new MockMultipartFile("event_request", "req.json", "application/json",
			readJson("/json/event/req.json").getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/event/add")
				.file(event_image)
				.file(event_request)
				.header("TOKEN", tokenAdmin.getToken())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestHeaders(
					headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
				),
				requestParts(
					partWithName("event_image").description("이미지 파일"),
					partWithName("event_request").description("Json 파일")),
				requestPartBody("event_request"),
				requestPartFields("event_request",
					fieldWithPath("title").description("제목"),
					fieldWithPath("content").description("내용"),
					fieldWithPath("url").description("이벤트 url"),
					fieldWithPath("start_date").description("게시 시작 날짜"),
					fieldWithPath("end_date").description("게시 마감 날짜")
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
	void getEvent() throws Exception {

		mockMvc.perform(
				get("/api/event/distribute")
					.param("status", "0")

			).andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestParameters(
						parameterWithName("status").description("진행 중인 케이스, 종료 된 케이스 분류")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data[0].id").description("id"),
						fieldWithPath("data[0].title").description("제목"),
						fieldWithPath("data[0].content").description("제목"),
						fieldWithPath("data[0].url").description("이벤트 url"),
						fieldWithPath("data[0].image").description("이미지"),
						fieldWithPath("data[0].status").description("진행, 종료 상태 값"),
						fieldWithPath("data[0].start_date").description("게시 시작 날짜"),
						fieldWithPath("data[0].end_date").description("게시 마감 날짜"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void getEventDetail() throws Exception {
		Long id = 1L;
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		mockMvc.perform(
				get("/api/event/{id}", id)
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
						fieldWithPath("data.id").description("번호"),
						fieldWithPath("data.title").description("제목"),
						fieldWithPath("data.content").description("내용"),
						fieldWithPath("data.url").description("이벤트 url"),
						fieldWithPath("data.image").description("이미지"),
						fieldWithPath("data.status").description("사용 유무"),
						fieldWithPath("data.user_name").description("등록한 관리자"),
						fieldWithPath("data.start_date").description("게시 시작 날짜"),
						fieldWithPath("data.end_date").description("게시 마감 날짜"),
						fieldWithPath("data.create_date").description("생성 시간"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void getEventPaging() throws Exception {
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		mockMvc.perform(
				get("/api/event")
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
						fieldWithPath("data.content").description("모든 Manager의 정보"),
						fieldWithPath("data.content[0].id").description("Manager id (pk)"),
						fieldWithPath("data.content[0].title").description("제목"),
						fieldWithPath("data.content[0].content").description("내용"),
						fieldWithPath("data.content[0].image").description("이미지"),
						fieldWithPath("data.content[0].url").description("Url"),
						fieldWithPath("data.content[0].status").description("게시여부"),
						fieldWithPath("data.content[0].user_name").description("생성한 관리자"),
						fieldWithPath("data.content[0].start_date").description("게시 시작날"),
						fieldWithPath("data.content[0].end_date").description("게시 마감날"),
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

	@Test
	@Transactional
	void editEventImage() throws Exception {
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		MockMultipartFile event_image = new MockMultipartFile("event_image", "bancow.png", "image/png",
			readImage("/img/bancow.png").getBytes());
		MockMultipartFile event_request = new MockMultipartFile("event_request", "update.json", "application/json",
			readJson("/json/event/update.json").getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(
				multipart("/api/event/edit")
					.file(event_image)
					.file(event_request)
					.header("TOKEN", tokenAdmin.getToken())
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestHeaders(
					headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
				),
				requestParts(
					partWithName("event_image").description("이미지 파일"),
					partWithName("event_request").description("Json 파일")),
				requestPartBody("event_request"),
				requestPartFields("event_request",
					fieldWithPath("id").description("번호"),
					fieldWithPath("title").description("제목"),
					fieldWithPath("content").description("내용"),
					fieldWithPath("url").description("내용"),
					fieldWithPath("start_date").description("게시 시작 날짜"),
					fieldWithPath("end_date").description("게시 마감 날짜"),
					fieldWithPath("status").description("상태 값")
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
	void editEventNotImage() throws Exception {
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		mockMvc.perform(
				patch("/api/event/edit")
					.contentType(MediaType.APPLICATION_JSON)
					.header("TOKEN", tokenAdmin.getToken())
					.content(readJson("/json/event/update.json"))
			)

			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestHeaders(
					headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
				),
				requestFields(
					fieldWithPath("id").description("번호"),
					fieldWithPath("title").description("제목"),
					fieldWithPath("content").description("내용"),
					fieldWithPath("url").description("이벤트 url"),
					fieldWithPath("start_date").description("게시 시작 날짜"),
					fieldWithPath("end_date").description("게시 마감 날짜"),
					fieldWithPath("status").description("상태 값")
				),
				responseFields(
					fieldWithPath("data").description("결과 데이터"),
					fieldWithPath("data.result").description("인증 성공 여부"),
					fieldWithPath("data.message").description("response 메시지"),
					fieldWithPath("status").description("HTTP Status")
				)
			));
	}

	@Test
	@Transactional
	void deleteEventOne() throws Exception {
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		mockMvc.perform(
				delete("/api/event/{id}", 1)
					.header("TOKEN", tokenAdmin.getToken())
					.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					pathParameters(
						parameterWithName("id").description("ID")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("문의 삭제 전송 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void deleteEventList() throws Exception {

		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		mockMvc.perform(
				delete("/api/event/delete")
					.header("TOKEN", tokenAdmin.getToken())
					.content(readJson("/json/delete.json"))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andDo(restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					requestFields(
						fieldWithPath("id").description("번호")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("문의 삭제 전송 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			);
	}
}