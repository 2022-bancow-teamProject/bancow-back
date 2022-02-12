package com.bancow.bancowback.domain.sub.farm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bancow.bancowback.domain.sub.farm.entity.Farm;

public interface FarmRepository extends JpaRepository<Farm, Long> {

	List<Farm> findByStatus(boolean b);
}
