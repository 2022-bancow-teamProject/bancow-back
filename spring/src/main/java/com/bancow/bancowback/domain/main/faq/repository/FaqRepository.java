package com.bancow.bancowback.domain.main.faq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bancow.bancowback.domain.main.faq.entity.Faq;

public interface FaqRepository extends JpaRepository<Faq, Long> {

	List<Faq> findByIdIn(List<Long> idList);

	List<Faq> findByTitleContaining(String word);
}
