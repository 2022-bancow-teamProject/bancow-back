package com.bancow.bancowback.domain.common.util.token.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancow.bancowback.domain.common.util.token.entity.Token;
import com.bancow.bancowback.domain.manager.entity.Manager;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
	Optional<Token> findByToken(String token);

	Optional<Token> findByManager(Manager manager);
}
