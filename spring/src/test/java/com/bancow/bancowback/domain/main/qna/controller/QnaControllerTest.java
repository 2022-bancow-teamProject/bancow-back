package com.bancow.bancowback.domain.main.qna.controller;

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

class QnaControllerTest extends TestSupport {

	@Autowired
	private TokenRepository tokenRepository;

	@Test
	@Transactional
	void getQna() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();

		mockMvc.perform(
			get("/api/qna/{id}", 1)
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
						fieldWithPath("data.category").description("문의 카테고리 (제휴, 투자, 기타)"),
						fieldWithPath("data.qna_name").description("문의자 이름"),
						fieldWithPath("data.phone_number").description("전화번호"),
						fieldWithPath("data.email").description("이메일"),
						fieldWithPath("data.title").description("제목"),
						fieldWithPath("data.message").description("메시지"),
						fieldWithPath("data.checked").description("Manager 답장 여부"),
						fieldWithPath("data.create_date").description("문의 날짜"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			);
	}

	@Test
	@Transactional
	void getQnaPaging() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
			get("/api/qna")
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
						fieldWithPath("data.content[0].category").description("문의 카테고리 (제휴, 투자, 기타)"),
						fieldWithPath("data.content[0].qna_name").description("문의자 이름"),
						fieldWithPath("data.content[0].phone_number").description("전화번호"),
						fieldWithPath("data.content[0].email").description("이메일"),
						fieldWithPath("data.content[0].title").description("제목"),
						fieldWithPath("data.content[0].message").description("메시지"),
						fieldWithPath("data.content[0].checked").description("동의 여부"),
						fieldWithPath("data.content[0].create_date").description("문의 날짜"),
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
	void deleteQna() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
			delete("/api/qna/{id}", 1)
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
	public void deleteQnaList() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
			delete("/api/qna/delete")
				.header("TOKEN", token.getToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(readJson("json/qna/deleteQnaList.json"))
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					requestFields(
						fieldWithPath("id").description("id 리스트")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("QnA 삭제 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void addQna() throws Exception {
		mockMvc.perform(
			post("/api/qna/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(readJson("/json/qna/addQna.json"))
				.accept(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestFields(
						fieldWithPath("category").description("카테고리"),
						fieldWithPath("qna_name").description("이름"),
						fieldWithPath("phone_number").description("전화번호"),
						fieldWithPath("email").description("이메일"),
						fieldWithPath("title").description("제목"),
						fieldWithPath("message").description("메시지")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("문의 추가 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void replyQna() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
			post("/api/qna/{id}/reply", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.header("TOKEN", token.getToken())
				.content(readJson("json/qna/replyQna.json"))
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
					requestFields(
						fieldWithPath("mail_title").description("메일 제목"),
						fieldWithPath("answer").description("메일 답변")
					),
					responseFields(
						fieldWithPath("data").description("결과 데이터"),
						fieldWithPath("data.result").description("문의 답변 이메일 전송 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;

	}
}