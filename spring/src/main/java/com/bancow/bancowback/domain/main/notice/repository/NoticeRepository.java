package com.bancow.bancowback.domain.main.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bancow.bancowback.domain.main.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

	List<Notice> findByIdIn(List<Long> idList);

}
