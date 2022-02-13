package com.bancow.bancowback.domain.main.faq.controller;

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

class FaqControllerTest extends TestSupport {

	@Autowired
	private TokenRepository tokenRepository;

	@Test
	@Transactional
	void getPublicFaq() throws Exception {
		Manager manager = adminManagerLogin();
		mockMvc.perform(
				get("/api/faq/public/{id}", 1)
					.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					pathParameters(
						parameterWithName("id").description("게시글 ID")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.id").description("아이디"),
						fieldWithPath("data.faq_category").description("문의 카테고리(새로운 상품, 신규 기능, 점검안내"),
						fieldWithPath("data.title").description("제목"),
						fieldWithPath("data.message").description("메시지"),
						fieldWithPath("data.status").description("공개여부"),
						fieldWithPath("data.create_date").description("글 작성일"),
						fieldWithPath("data.update_date").description("글 수정일"),
						fieldWithPath("data.username").description("작성자 이름"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void getPublicFaqPaging() throws Exception {
		Manager manager = adminManagerLogin();
		mockMvc.perform(
				get("/api/faq/public")
					.param("page", "0")
					.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestParameters(
						parameterWithName("page").description("페이지 번호 (0부터 시작)")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.content").description("모든 문의 정보"),
						fieldWithPath("data.content[0].id").description("아이디"),
						fieldWithPath("data.content[0].faq_category").description("문의 카테고리 (새로운 상품, 신규기능, 점검안내)"),
						fieldWithPath("data.content[0].title").description("제목"),
						fieldWithPath("data.content[0].message").description("메시지"),
						fieldWithPath("data.content[0].status").description("공개 여부"),
						fieldWithPath("data.content[0].create_date").description("글 등록일"),
						fieldWithPath("data.content[0].update_date").description("글 수정일"),
						fieldWithPath("data.content[0].username").description("유저이름"),
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
						fieldWithPath("status").description("HTTP Status"))
				)

			)
		;
	}

	@Test
	@Transactional
	void getFaq() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();

		mockMvc.perform(
				get("/api/faq/{id}", 1)
					.accept(MediaType.APPLICATION_JSON)
					.header("TOKEN", token.getToken())
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					pathParameters(
						parameterWithName("id").description("게시글 ID")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.id").description("아이디"),
						fieldWithPath("data.faq_category").description("문의 카테고리(새로운 상품, 신규 기능, 점검안내"),
						fieldWithPath("data.title").description("제목"),
						fieldWithPath("data.message").description("메시지"),
						fieldWithPath("data.status").description("공개여부"),
						fieldWithPath("data.create_date").description("글 작성일"),
						fieldWithPath("data.update_date").description("글 수정일"),
						fieldWithPath("data.username").description("작성자 이름"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void getFaqPaging() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
				get("/api/faq")
					.param("page", "0")
					.header("TOKEN", token.getToken())
					.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					requestParameters(
						parameterWithName("page").description("페이지 번호 (0부터 시작)")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.content").description("모든 문의 정보"),
						fieldWithPath("data.content[0].id").description("아이디"),
						fieldWithPath("data.content[0].faq_category").description("문의 카테고리 (새로운 상품, 신규기능, 점검안내)"),
						fieldWithPath("data.content[0].title").description("제목"),
						fieldWithPath("data.content[0].message").description("메시지"),
						fieldWithPath("data.content[0].status").description("공개 여부"),
						fieldWithPath("data.content[0].create_date").description("글 등록일"),
						fieldWithPath("data.content[0].update_date").description("글 수정일"),
						fieldWithPath("data.content[0].username").description("유저이름"),
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
						fieldWithPath("status").description("HTTP Status"))
				)

			)
		;
	}

	@Test
	@Transactional
	void addFaq() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
				post("/api/faq/add")
					.header("TOKEN", token.getToken())
					.contentType(MediaType.APPLICATION_JSON)
					.content(readJson("json/faq/addFaq.json"))
					.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					requestFields(
						fieldWithPath("faq_category").description("카테고리"),
						fieldWithPath("title").description("제목"),
						fieldWithPath("message").description("메시지"),
						fieldWithPath("status").description("공개 여부")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("성공여부 BOOLEAN"),
						fieldWithPath("data.message").description("성공여부 메세지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void deleteFaq() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
				delete("/api/faq/delete/{id}", 1)
					.header("TOKEN", token.getToken())
					.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					pathParameters(
						parameterWithName("id").description("게시글 ID")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("FAQ 삭제 전송 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void deleteFaqList() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
			delete("/api/faq/delete")
				.header("TOKEN", token.getToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(readJson("json/faq/deleteFaqList.json"))
					.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					requestFields(
						fieldWithPath("id_list").description("삭제할 항목")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("문의 삭제 전송 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void updateFaq() throws Exception {

		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();

		mockMvc.perform(
			patch("/api/faq/update", 1)
				.header("TOKEN", token.getToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(readJson("json/faq/updateFaq.json"))
					.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					requestFields(
						fieldWithPath("id").description("id"),
						fieldWithPath("faq_category").description("카테고리"),
						fieldWithPath("title").description("제목"),
						fieldWithPath("message").description("메시지"),
						fieldWithPath("status").description("공개 여부")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("문의 삭제 전송 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	public void search() throws Exception {
		mockMvc.perform(
			get("/api/faq/search")
				.param("word", "구매")
				.param("page", "0")
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestParameters(
						parameterWithName("word").description("검색어"),
						parameterWithName("page").description("페이징 처리를 위한 페이지 값 (0부터 시작)")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.content").description("모든 검색 결과 정보"),
						fieldWithPath("data.content[0].id").description("FAQ id"),
						fieldWithPath("data.content[0].title").description("FAQ 제목"),
						fieldWithPath("data.content[0].message").description("FAQ 메시지"),
						fieldWithPath("data.content[0].status").description("FAQ 상태"),
						fieldWithPath("data.content[0].faq_category").description("FAQ 카테고리"),
						fieldWithPath("data.content[0].create_date").description("FAQ 만든 날짜"),
						fieldWithPath("data.content[0].update_date").description("FAQ 수정 날짜"),

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
			)
		;
	}
}