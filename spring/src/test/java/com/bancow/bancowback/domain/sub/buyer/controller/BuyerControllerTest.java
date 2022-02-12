package com.bancow.bancowback.domain.sub.buyer.controller;

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

class BuyerControllerTest extends TestSupport {

	@Autowired
	private TokenRepository tokenRepository;

	@Test
	@Transactional
	void getBuyerDistribute() throws Exception {
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

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

}