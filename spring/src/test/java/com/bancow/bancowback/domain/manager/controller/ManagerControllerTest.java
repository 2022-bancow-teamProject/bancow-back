package com.bancow.bancowback.domain.manager.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.bancow.bancowback.TestSupport;
import com.bancow.bancowback.domain.common.util.token.entity.Token;
import com.bancow.bancowback.domain.common.util.token.repository.TokenRepository;
import com.bancow.bancowback.domain.manager.dto.ManagerLoginDto;
import com.bancow.bancowback.domain.manager.entity.Manager;
import com.bancow.bancowback.domain.manager.repository.ManagerRepository;
import com.bancow.bancowback.domain.manager.service.ManagerService;

class ManagerControllerTest extends TestSupport {

	@Autowired
	private ManagerService managerService;

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private TokenRepository tokenRepository;

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

	@Test
	void login() throws Exception {
		mockMvc.perform(
			post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					"{\n"
						+ "  \"email\": \"smtptestkk@gmail.com\",\n"
						+ "  \"password\": \"1111\"\n"
						+ "}"
				)
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestFields(
						fieldWithPath("email").description("이메일"),
						fieldWithPath("password").description("비밀번호")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("로그인 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	void logout() throws Exception {
		ManagerLoginDto managerLoginDto =
			ManagerLoginDto.builder()
				.email("smtptestkk@gmail.com")
				.password("1111")
				.build();
		managerService.loginManager(managerLoginDto);
		Manager manager = managerRepository.findByEmail(managerLoginDto.getEmail()).get();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
			post("/api/logout")
				.contentType(MediaType.APPLICATION_JSON)
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
						fieldWithPath("data.result").description("로그아웃 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			);
	}

}