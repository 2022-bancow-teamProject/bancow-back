package com.bancow.bancowback.domain.main.farmqna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancow.bancowback.domain.main.farmqna.entity.FarmQna;

@Repository
public interface FarmQnaRepository extends JpaRepository<FarmQna, Long> {

}
