package com.bancow.bancowback.domain.main.faq.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bancow.bancowback.domain.main.faq.entity.Faq;

public interface FaqRepository extends JpaRepository<Faq, Long> {

	List<Faq> findByIdIn(List<Long> idList);

	@Query("select f from Faq f where f.title like %:#{#word}%")
	Page<Faq> findByTitleContainingWithPagination(String word, Pageable pageable);
}
