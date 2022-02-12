package com.bancow.bancowback.domain.main.notice.controller;

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

class NoticeControllerTest extends TestSupport {

	@Autowired
	private TokenRepository tokenRepository;

	@Test
	@Transactional
	void getPublicNotice() throws Exception {
		Manager manager = adminManagerLogin();
		mockMvc.perform(
				get("/api/notice/public/{id}", 1)
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
						fieldWithPath("data.noticeCategory").description("문의 카테고리(새로운 상품, 신규 기능, 점검안내"),
						fieldWithPath("data.title").description("제목"),
						fieldWithPath("data.message").description("메시지"),
						fieldWithPath("data.status").description("공개여부"),
						fieldWithPath("data.createDate").description("글 작성일"),
						fieldWithPath("data.updateDate").description("글 수정일"),
						fieldWithPath("data.username").description("작성자 이름"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void getPublicNoticePaging() throws Exception {
		Manager manager = adminManagerLogin();
		mockMvc.perform(
				get("/api/notice/public")
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
						fieldWithPath("data.content[0].noticeCategory").description("문의 카테고리 (새로운 상품, 신규기능, 점검안내)"),
						fieldWithPath("data.content[0].title").description("제목"),
						fieldWithPath("data.content[0].message").description("메시지"),
						fieldWithPath("data.content[0].status").description("공개 여부"),
						fieldWithPath("data.content[0].createDate").description("글 등록일"),
						fieldWithPath("data.content[0].updateDate").description("글 수정일"),
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
	void getNotice() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();

		mockMvc.perform(
				get("/api/notice/{id}", 1)
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
						fieldWithPath("data.noticeCategory").description("문의 카테고리(새로운 상품, 신규 기능, 점검안내"),
						fieldWithPath("data.title").description("제목"),
						fieldWithPath("data.message").description("메시지"),
						fieldWithPath("data.status").description("공개여부"),
						fieldWithPath("data.createDate").description("글 작성일"),
						fieldWithPath("data.updateDate").description("글 수정일"),
						fieldWithPath("data.username").description("작성자 이름"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void getNoticePaging() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
				get("/api/notice")
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
						fieldWithPath("data.content[0].noticeCategory").description("문의 카테고리 (새로운 상품, 신규기능, 점검안내)"),
						fieldWithPath("data.content[0].title").description("제목"),
						fieldWithPath("data.content[0].message").description("메시지"),
						fieldWithPath("data.content[0].status").description("공개 여부"),
						fieldWithPath("data.content[0].createDate").description("글 등록일"),
						fieldWithPath("data.content[0].updateDate").description("글 수정일"),
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
	void addNotice() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
				post("/api/notice/add")
					.header("TOKEN", token.getToken())
					.contentType(MediaType.APPLICATION_JSON)
					.content(
						"{"
							+ "  \"noticeCategory\": \"INSPECTION\",\n"
							+ "  \"title\": \"공지사항 제목입니다..\",\n"
							+ "  \"message\": \"공지사항 내용입니다.\",\n"
							+ "  \"status\": false\n"
							+ "}"
					)
					.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					requestFields(
						fieldWithPath("noticeCategory").description("카테고리"),
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
	void deleteNotice() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
				delete("/api/notice/delete/{id}", 1)
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
						fieldWithPath("data.result").description("공지사항 삭제 전송 성공 여부"),
						fieldWithPath("data.message").description("response 메시지"),
						fieldWithPath("status").description("HTTP Status")
					)
				)
			)
		;
	}

	@Test
	@Transactional
	void deleteNoticeList() throws Exception {
		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();
		mockMvc.perform(
				delete("/api/notice/delete")
					.header("TOKEN", token.getToken())
					.contentType(MediaType.APPLICATION_JSON)
					.content(
						"{"
							+ "  \"idList\": [1]\n"
							+ "}"
					)
					.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName("TOKEN").description("해당 로그인 유저의 토큰값")
					),
					requestFields(
						fieldWithPath("idList").description("삭제할 항목")
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
	void updateNotice() throws Exception {

		Manager manager = adminManagerLogin();
		Token token = tokenRepository.findByManager(manager).get();

		mockMvc.perform(
				patch("/api/notice/update", 1)
					.header("TOKEN", token.getToken())
					.contentType(MediaType.APPLICATION_JSON)
					.content(
						"{"
							+ "  \"id\": \"1\",\n"
							+ "  \"noticeCategory\": \"INSPECTION\",\n"
							+ "  \"title\": \"수정된 공지사항 제목입니다.\",\n"
							+ "  \"message\": \"수정된 공지사항 내용입니다.\",\n"
							+ "  \"status\": false\n"
							+ "}")
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
						fieldWithPath("noticeCategory").description("카테고리"),
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
}