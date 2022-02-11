package com.bancow.bancowback.domain.main.farmqna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bancow.bancowback.domain.main.farmqna.entity.FarmQna;

@Repository
public interface FarmQnaRepository extends JpaRepository<FarmQna, Long> {

	List<FarmQna> findByIdIn(List<Long> idList);

	@Query("select count(f) from FarmQna f")
	Integer countFarmQna();

	@Query("select count(f) from FarmQna f where f.checked = false")
	Integer uncheckedFarmQna();
}
