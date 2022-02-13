package com.bancow.bancowback.domain.sub.howto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.sub.howto.Mapper.HowtoMapper;
import com.bancow.bancowback.domain.sub.howto.entity.Howto;
import com.bancow.bancowback.domain.sub.howto.repository.HowtoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HowtoService {

	private final HowtoRepository howtoRepository;
	private final HowtoMapper howtoMapper;

	public List<Howto> getHowto() {

		return howtoRepository.findAll().stream()
			.map(howto -> howtoMapper.toHowto(howto))
			.collect(Collectors.toList());
	}
}
