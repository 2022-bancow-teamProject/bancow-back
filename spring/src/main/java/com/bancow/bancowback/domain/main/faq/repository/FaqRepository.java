package com.bancow.bancowback.domain.main.faq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancow.bancowback.domain.main.faq.entity.Faq;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {

	List<Faq> findByIdIn(List<Long> idList);

}
