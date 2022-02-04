package com.bancow.bancowback.domain.manager.controller;

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
import com.bancow.bancowback.domain.manager.dto.ManagerLoginDto;
import com.bancow.bancowback.domain.manager.dto.ManagerRegisterDto;
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
	@Transactional
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
	@Transactional
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
	@Transactional
	void logout() throws Exception {
		Manager manager = superManagerLogin();
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

	@Test
	@Transactional
	void authentication() throws Exception {
		Manager manager = managerRegister();
		Token token = tokenRepository.findByManager(manager).get();

		mockMvc.perform(
			get("/api/authentication/{token}", token.getToken())
				.accept(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					pathParameters(
						parameterWithName("token").description("회원가입한 manager의 토큰 값")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("인증 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			);
	}

	@Test
	@Transactional
	void statusToAdmin() throws Exception {

		Manager manager = managerRegister();

		Manager superManager = superManagerLogin();
		Token tokenSuper = tokenRepository.findByManager(superManager).get();

		mockMvc.perform(
			patch("/api/statusadmin")
				.param("id", String.valueOf(manager.getId()))
				.header("TOKEN", tokenSuper.getToken())
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("로그인한 SUPER 계정의 토큰값")
					),
					requestParameters(
						parameterWithName("id").description("권한을 변경할 Manager의 Id")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("인증 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			);
	}

	@Test
	@Transactional
	void findAllManager() throws Exception {

		Manager manager = managerRegister();
		Manager superManager = superManagerLogin();
		Token tokenSuper = tokenRepository.findByManager(superManager).get();

		mockMvc.perform(
			get("/api/allmanager")
				.header("TOKEN", tokenSuper.getToken())
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("로그인한 SUPER 계정의 토큰값")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data[0].email").description("이메일"),
						fieldWithPath("data[0].username").description("이름"),
						fieldWithPath("data[0].managerStatus").description("Manager 상태"),
						fieldWithPath("data[0].createDate").description("Manager 생성일"),
						fieldWithPath("data[0].updateDate").description("Manager 수정일"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			);

	}

	private Manager superManagerLogin() {
		ManagerLoginDto managerLoginDto =
			ManagerLoginDto.builder()
				.email("smtptestkk@gmail.com")
				.password("1111")
				.build();
		managerService.loginManager(managerLoginDto);
		return managerRepository.findByEmail(managerLoginDto.getEmail()).get();
	}

	private Manager managerRegister() {
		ManagerRegisterDto managerRegisterDto =
			ManagerRegisterDto.builder()
				.email("gmldnr2222@naver.com")
				.password("비밀번호")
				.password2("비밀번호")
				.username("가나다")
				.build();
		managerService.registerManager(managerRegisterDto);
		return managerRepository.findByEmail(managerRegisterDto.getEmail()).get();
	}
}