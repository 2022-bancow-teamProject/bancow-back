package com.bancow.bancowback.domain.common.util.mail.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.util.mail.MailComponent;
import com.bancow.bancowback.domain.common.util.mail.entity.MailTemplate;
import com.bancow.bancowback.domain.common.util.mail.repository.MailTemplateRepository;
import com.bancow.bancowback.domain.common.util.token.repository.TokenRepository;
import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.main.farmqna.dto.FarmQnaReplyDto;
import com.bancow.bancowback.domain.main.farmqna.entity.FarmQna;
import com.bancow.bancowback.domain.main.qna.dto.QnaReplyDto;
import com.bancow.bancowback.domain.main.qna.entity.Qna;
import com.bancow.bancowback.domain.manager.entity.Manager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	private final TokenService tokenService;

	private final MailTemplateRepository mailTemplateRepository;
	private final MailComponent mailComponent;

	public void sendMail(Manager manager, String templateId) {
		String serverURL = "http://localhost:8080";

		String userAuthenticationKey = tokenService.makeJwtToken(manager);
		tokenService.saveByManager(manager);

		Optional<MailTemplate> optionalMailTemplate = mailTemplateRepository.findByTemplateId(templateId);
		optionalMailTemplate.ifPresent(e -> {
			String fromEmail = e.getSendEmail();
			String fromUserName = e.getSendUserName();
			String title = e.getTitle().replaceAll("\\{USER_NAME\\}", manager.getUsername());
			String contents = e.getContents().replaceAll("\\{USER_NAME\\}", manager.getUsername())
				.replaceAll("\\{SERVER_URL\\}", serverURL)
				.replaceAll("\\{USER_AUTHENTICATION_KEY\\}", userAuthenticationKey);

			mailComponent.send(fromEmail, fromUserName, manager.getEmail(), manager.getUsername(), title, contents);
		});
	}

	public void sendReplyMail(FarmQna farmQna, FarmQnaReplyDto dto, String templateId) {

		Optional<MailTemplate> optionalMailTemplate = mailTemplateRepository.findByTemplateId(templateId);
		optionalMailTemplate.ifPresent(e -> {
			String fromEmail = e.getSendEmail();
			String fromUserName = e.getSendUserName();
			String title = e.getTitle().replaceAll("\\{USER_NAME\\}", farmQna.getFarmQnaName())
				.replaceAll("\\{TITLE\\}", dto.getMailTitle());
			String contents = e.getContents().replaceAll("\\{ANSWER\\}", dto.getAnswer());

			mailComponent.send(fromEmail, fromUserName, farmQna.getEmail(), farmQna.getFarmQnaName(), title, contents);
		});
	}

	public void sendQnaReplyMail(Qna qna, QnaReplyDto dto, String templateId) {

		Optional<MailTemplate> optionalMailTemplate = mailTemplateRepository.findByTemplateId(templateId);
		optionalMailTemplate.ifPresent(e -> {
			String fromEmail = e.getSendEmail();
			String fromUserName = e.getSendUserName();
			String title = e.getTitle().replaceAll("\\{USER_NAME\\}", qna.getQnaName())
				.replaceAll("\\{TITLE\\}", dto.getMailTitle());
			String contents = e.getContents().replaceAll("\\{ANSWER\\}", dto.getAnswer());

			mailComponent.send(fromEmail, fromUserName, qna.getEmail(), qna.getQnaName(), title, contents);
		});
	}
}
