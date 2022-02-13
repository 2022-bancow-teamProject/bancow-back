package com.bancow.bancowback.domain.sub.howto.Mapper;

import org.springframework.stereotype.Component;

import com.bancow.bancowback.domain.sub.howto.entity.Howto;

@Component
public class HowtoMapper {

	public Howto toHowto(Howto howto) {
		return Howto.builder()
			.id(howto.getId())
			.movieName(howto.getMovieName())
			.movieUrl("https://kr.object.ncloudstorage.com/bancowback/" + howto.getMovieUrl())
			.build();
	}
}
