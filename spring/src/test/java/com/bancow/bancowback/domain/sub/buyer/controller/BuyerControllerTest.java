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
	void getBuyerPaging() throws Exception {
		Manager adminManager = adminManagerLogin();
		Token tokenAdmin = tokenRepository.findByManager(adminManager).get();

		mockMvc.perform(
				get("/api/buyer")
					.param("page", "0")
					.header("TOKEN", tokenAdmin.getToken())
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					requestParameters(
						parameterWithName("page").description("페이징 처리를 위한 페이지 값 (0부터 시작)")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.content").description("정보"),
						fieldWithPath("data.content[0].id").description("id (pk)"),
						fieldWithPath("data.content[0].title").description("제목"),
						fieldWithPath("data.content[0].buyer_name").description("구매자이름"),
						fieldWithPath("data.content[0].user_name").description("관리자 이름"),
						fieldWithPath("data.content[0].farm_name").description("농장이름"),
						fieldWithPath("data.content[0].status").description("배포 상태 값"),
						fieldWithPath("data.content[0].create_date").description("생성된 시간"),
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
					)
				);

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