package com.bancow.bancowback.domain.main.qna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancow.bancowback.domain.main.qna.entity.QnaReply;

@Repository
public interface QnaReplyRepository extends JpaRepository<QnaReply, Long> {
}
