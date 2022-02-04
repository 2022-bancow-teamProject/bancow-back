package com.bancow.bancowback.domain.common.util.token.service;

import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.exception.BizException;
import com.bancow.bancowback.domain.common.util.token.entity.Token;
import com.bancow.bancowback.domain.common.util.token.repository.TokenRepository;
import com.bancow.bancowback.domain.manager.entity.ManagerStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final TokenRepository tokenRepository;

	public String findUsernameByToken(String token) {
		Token findToken = tokenRepository.findByToken(token)
			.orElseThrow(() -> new BizException("해당 토큰을 찾을 수 없습니다."));

		return findToken.getManager().getUsername();
	}

	public void checkTokenManager(String token) {
		Token findToken = tokenRepository.findByToken(token)
			.orElseThrow(() -> new BizException("Not Found Token"));
		if (!(findToken.getManager().getManagerStatus().equals(ManagerStatus.ADMIN) ||
			findToken.getManager().getManagerStatus().equals(ManagerStatus.SUPER))) {
			throw new BizException("유저 권한이 없습니다.");
		}
	}

	public void checkTokenSuper(String token) {
		Token findToken = tokenRepository.findByToken(token)
			.orElseThrow(() -> new BizException("Not Found Token"));
		if (!(findToken.getManager().getManagerStatus().equals(ManagerStatus.SUPER))) {
			throw new BizException("SUPER 계정이 아닙니다.");
		}
	}
}
