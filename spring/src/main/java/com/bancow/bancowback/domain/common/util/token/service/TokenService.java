package com.bancow.bancowback.domain.common.util.token.service;

import static com.bancow.bancowback.domain.common.exception.ErrorCode.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bancow.bancowback.domain.common.exception.TokenAuthorityException;
import com.bancow.bancowback.domain.common.exception.TokenAuthoritySuperException;
import com.bancow.bancowback.domain.common.exception.TokenNotFoundException;
import com.bancow.bancowback.domain.common.util.token.entity.Token;
import com.bancow.bancowback.domain.common.util.token.repository.TokenRepository;
import com.bancow.bancowback.domain.manager.entity.Manager;
import com.bancow.bancowback.domain.manager.entity.ManagerStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final TokenRepository tokenRepository;

	public Manager getManager(String token) {
		return getToken(token).getManager();
	}

	public Token getToken(String token){
		return tokenRepository.findByToken(token)
			.orElseThrow(() -> new TokenNotFoundException(NOT_FOUND_TOKEN, "해당 토큰을 찾을 수 없습니다."));
	}

	public void validTokenAuthority(String token) {
		Token findToken = getToken(token);
		if (!(findToken.getManager().getManagerStatus().equals(ManagerStatus.ADMIN) ||
			findToken.getManager().getManagerStatus().equals(ManagerStatus.SUPER))) {
			throw new TokenAuthorityException(NOT_AUTHORITY, "유저 권한이 없습니다.");
		}
	}

	public void validTokenSuper(String token) {
		Token findToken = getToken(token);
		if (!(findToken.getManager().getManagerStatus().equals(ManagerStatus.SUPER))) {
			throw new TokenAuthoritySuperException(NOT_AUTHORITY_SUPER, "SUPER 계정이 아닙니다.");
		}
	}

	public String makeJwtToken(Manager manager) {
		LocalDateTime expiredDatetime = LocalDateTime.now().plusMonths(1);
		Date expiredDate = java.sql.Timestamp.valueOf(expiredDatetime);
		return JWT.create()
			.withExpiresAt(expiredDate)
			.withClaim("user_id", manager.getId())
			.withSubject(manager.getUsername())
			.withIssuer(manager.getEmail())
			.sign(Algorithm.HMAC512("bancowAlgorithm".getBytes()));
	}

	public Optional<Token> findByToken(String token) {
		return tokenRepository.findByToken(token);
	}

	public void delete(Token token) {
		tokenRepository.delete(token);
	}

	public Token saveByManager(Manager manager) {
		String tokenString = makeJwtToken(manager);
		Token token = Token.builder()
			.token(tokenString)
			.manager(manager)
			.expiredDate(LocalDateTime.now().plusDays(1))
			.build();

		return tokenRepository.save(token);
	}
}
