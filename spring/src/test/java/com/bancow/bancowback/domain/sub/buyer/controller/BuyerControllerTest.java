package com.bancow.bancowback.domain.sub.buyer.controller;

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
import com.bancow.bancowback.domain.manager.entity.Manager;

class BuyerControllerTest extends TestSupport {

	@Autowired
	private TokenRepository tokenRepository;

	@Test
	@Transactional
	void getBuyerDistribute() throws Exception {

		mockMvc.perform(
				get("/api/buyer/distribute")

			).andExpect(status().isOk())
			.andDo(
				restDocs.document(
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data[0].id").description("id"),
						fieldWithPath("data[0].buyer_name").description("구매자 이름"),
						fieldWithPath("data[0].farm_name").description("농장 이름"),
						fieldWithPath("data[0].farm_ceo_name").description("농장 대표님 이름"),
						fieldWithPath("data[0].farm_image").description("농장 사진"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void editBuyer() throws Exception {
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		mockMvc.perform(
				patch("/api/buyer/edit")
					.contentType(MediaType.APPLICATION_JSON)
					.header("TOKEN", tokenAdmin.getToken())
					.content(readJson("/json/buyer/update.json"))
			)

			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestHeaders(
					headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
				),
				requestFields(
					fieldWithPath("id").description("번호"),
					fieldWithPath("status").description("공개여부 값")
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
	void deleteBuyerOne() throws Exception {
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		mockMvc.perform(
				delete("/api/buyer/{id}", 1)
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
						fieldWithPath("data.result").description("삭제 전송 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

		@Test
		@Transactional
		void deleteBuyerList() throws Exception {

			Manager adminManager = adminManagerLogin();
			Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

			mockMvc.perform(
					delete("/api/buyer/delete")
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