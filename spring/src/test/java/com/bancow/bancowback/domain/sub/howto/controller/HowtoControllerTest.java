package com.bancow.bancowback.domain.sub.howto.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bancow.bancowback.TestSupport;
import com.bancow.bancowback.domain.common.util.token.entity.Token;
import com.bancow.bancowback.domain.common.util.token.repository.TokenRepository;
import com.bancow.bancowback.domain.manager.entity.Manager;

class HowtoControllerTest extends TestSupport {
	@Autowired
	private TokenRepository tokenRepository;

	@Test
	@Transactional
	void getHowto() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
				get("/api/howto")

			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(

					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data[0].id").description("결과 데이터"),
						fieldWithPath("data[0].movie_name").description("영상 이름"),
						fieldWithPath("data[0].movie_url").description("영상 url"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}
}