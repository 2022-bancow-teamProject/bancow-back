package com.bancow.bancowback.domain.main.popup.controller;

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

}