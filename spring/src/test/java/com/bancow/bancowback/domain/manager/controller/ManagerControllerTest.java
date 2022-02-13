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
import com.bancow.bancowback.domain.manager.dto.ManagerFindDto;
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
				.content(readJson("json/manager/registerManger.json"))
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
				.content(readJson("json/manager/login.json"))
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
						fieldWithPath("data.token").description("로그인 토큰"),
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
				.param("page", "0")
				.header("TOKEN", tokenSuper.getToken())
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
						fieldWithPath("data.content[0].email").description("이메일"),
						fieldWithPath("data.content[0].username").description("이름"),
						fieldWithPath("data.content[0].managerStatus").description("Manager 상태"),
						fieldWithPath("data.content[0].create_date").description("Manager 생성일"),
						fieldWithPath("data.content[0].update_date").description("Manager 수정일"),
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
	void findManager() throws Exception {
		managerRegister();

		mockMvc.perform(
			post("/api/findmanager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(readJson("json/manager/findManager.json"))
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestFields(
						fieldWithPath("email").description("이메일"),
						fieldWithPath("username").description("이름")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("이메일 전송 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;

	}

	@Test
	@Transactional
	void authenticationPassword() throws Exception {
		Manager manager = managerRegister();
		Token registerToken = tokenRepository.findByManager(manager).get();
		managerService.authentication(registerToken.getToken());

		ManagerFindDto managerFindDto = ManagerFindDto.builder()
			.email("gmldnr2222@naver.com")
			.username("가나다")
			.build();

		managerService.findManager(managerFindDto);
		Token findToken = tokenRepository.findByManager(manager).get();

		mockMvc.perform(
			get("/api//authentication/findmanager/{token}", findToken.getToken())
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					pathParameters(
						parameterWithName("token").description("회원가입한 manager의 토큰 값")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("이메일 인증 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void changePassword() throws Exception {
		Manager manager = managerRegister();
		Token registerToken = tokenRepository.findByManager(manager).get();
		managerService.authentication(registerToken.getToken());

		ManagerFindDto managerFindDto = ManagerFindDto.builder()
			.email("gmldnr2222@naver.com")
			.username("가나다")
			.build();

		managerService.findManager(managerFindDto);
		Token findToken = tokenRepository.findByManager(manager).get();
		managerService.authenticationPassword(findToken.getToken());

		mockMvc.perform(
			patch("/api//authentication/findmanager/{token}/change-password", findToken.getToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(readJson("/json/manager/changePassword.json"))
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					pathParameters(
						parameterWithName("token").description("회원가입한 manager의 토큰 값")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("이메일 인증 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	private Manager superManagerLogin() {
		ManagerLoginDto managerLoginDto =
			ManagerLoginDto.builder()
				.email("smtptestkk@gmail.com")
				.password("q1w2e3r4")
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