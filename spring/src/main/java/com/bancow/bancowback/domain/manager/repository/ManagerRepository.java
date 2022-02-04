package com.bancow.bancowback.domain.manager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancow.bancowback.domain.manager.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
	Optional<Manager> findByEmail(String email);
}