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
			readJson("/json/req.json").getBytes(StandardCharsets.UTF_8));

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
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		mockMvc.perform(
				get("/api/event/distribute")

			).andExpect(status().isOk())
			.andDo(
				restDocs.document(
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data[0].id").description("id"),
						fieldWithPath("data[0].image").description("이미지"),
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
	void editEventImage() throws Exception {
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		MockMultipartFile event_image = new MockMultipartFile("event_image", "bancow.png", "image/png",
			readImage("/img/bancow.png").getBytes());
		MockMultipartFile event_request = new MockMultipartFile("event_request", "update.json", "application/json",
			readJson("/json/update.json").getBytes(StandardCharsets.UTF_8));

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
					.content(readJson("/json/update.json"))
			)

			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestHeaders(
					headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
				),
				requestFields(
					fieldWithPath("id").description("번호"),
					fieldWithPath("title").description("제목"),
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