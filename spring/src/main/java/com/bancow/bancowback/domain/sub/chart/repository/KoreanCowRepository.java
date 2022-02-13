package com.bancow.bancowback.domain.sub.chart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bancow.bancowback.domain.sub.chart.entity.KoreanCow;
import com.bancow.bancowback.domain.sub.chart.entity.KoreanCowCategory;

public interface KoreanCowRepository extends JpaRepository<KoreanCow, Long> {

	List<KoreanCow> findByCategory(KoreanCowCategory category);
}
