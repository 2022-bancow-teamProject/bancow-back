package com.bancow.bancowback.domain.main.bancowhistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bancow.bancowback.domain.main.bancowhistory.entity.BancowHistory;

public interface BancowHistoryRepository extends JpaRepository<BancowHistory, Long> {


}
