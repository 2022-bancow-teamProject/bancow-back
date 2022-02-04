package com.bancow.bancowback.domain.common.util.mail.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancow.bancowback.domain.common.util.mail.entity.MailTemplate;

@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long> {
	Optional<MailTemplate> findByTemplateId(String templateId);
}
