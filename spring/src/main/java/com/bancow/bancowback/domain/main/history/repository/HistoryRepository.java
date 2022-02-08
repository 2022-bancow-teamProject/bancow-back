package com.bancow.bancowback.domain.main.history.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bancow.bancowback.domain.main.history.entity.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

	Optional<List<History>> findByIdIn(List<Long> idList);
}
