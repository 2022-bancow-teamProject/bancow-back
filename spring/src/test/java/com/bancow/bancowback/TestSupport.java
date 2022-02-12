package com.bancow.bancowback;

import io.micrometer.core.instrument.util.IOUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bancow.bancowback.domain.manager.dto.ManagerLoginDto;
import com.bancow.bancowback.domain.manager.entity.Manager;
import com.bancow.bancowback.domain.manager.repository.ManagerRepository;
import com.bancow.bancowback.domain.manager.service.ManagerService;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@Import(RestDocsConfiguration.class)
public class TestSupport {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected RestDocumentationResultHandler restDocs;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private ResourceLoader resourceLoader;

	@BeforeEach
	void setUp(
		final WebApplicationContext context,
		final RestDocumentationContextProvider provider
	) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
			.apply(MockMvcRestDocumentation.documentationConfiguration(provider))
			.alwaysDo(MockMvcResultHandlers.print())
			.alwaysDo(restDocs)
			.build();
	}

	public Manager adminManagerLogin() {
		ManagerLoginDto managerLoginDto =
			ManagerLoginDto.builder()
				.email("admin@gmail.com")
				.password("q1w2e3r4")
				.build();
		managerService.loginManager(managerLoginDto);
		return managerRepository.findByEmail(managerLoginDto.getEmail()).get();
	}

	protected String readJson(final String path) throws IOException {
		return IOUtils.toString(resourceLoader.getResource("classpath:" + path).getInputStream(),
			StandardCharsets.UTF_8);
	}
	protected String readImage(final String path) throws IOException {
		return IOUtils.toString(resourceLoader.getResource("classpath:" + path).getInputStream());
	}
}
