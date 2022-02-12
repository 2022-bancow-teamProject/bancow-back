package com.bancow.bancowback.domain.main.notice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bancow.bancowback.domain.main.notice.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

	List<Notice> findByIdIn(List<Long> idList);

}
