package com.bancow.bancowback.manager.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.bancow.bancowback.TestSupport;

class ManagerControllerTest extends TestSupport {

	@Test
	void registerManager() throws Exception {
		mockMvc.perform(
			post("/api/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					"{\n"
						+ "  \"email\": \"gmldnr2222@naver.com\",\n"
						+ "  \"username\": \"김지훈\",\n"
						+ "  \"password\": \"passwordAndPassword2\",\n"
						+ "  \"password2\": \"passwordAndPassword2\"\n"
						+ "}"
				)
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestFields(
						fieldWithPath("email").description("이메일"),
						fieldWithPath("username").description("이름"),
						fieldWithPath("password").description("비밀번호"),
						fieldWithPath("password2").description("비밀번호 확인")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("회원가입 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

}