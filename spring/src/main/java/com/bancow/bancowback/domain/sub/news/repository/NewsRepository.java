package com.bancow.bancowback.domain.sub.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancow.bancowback.domain.sub.news.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

	List<News> findByIdIn(List<Long> idList);
}
