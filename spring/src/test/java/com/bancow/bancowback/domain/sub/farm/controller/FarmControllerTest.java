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

}
