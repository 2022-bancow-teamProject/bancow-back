package com.bancow.bancowback.util.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancow.bancowback.util.token.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
}
