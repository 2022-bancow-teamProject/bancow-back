package com.bancow.bancowback.common.util.token.service;

import org.springframework.stereotype.Service;

import com.bancow.bancowback.common.exception.BizException;
import com.bancow.bancowback.common.util.token.entity.Token;
import com.bancow.bancowback.common.util.token.repository.TokenRepository;

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
}
