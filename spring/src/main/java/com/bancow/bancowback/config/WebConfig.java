package com.bancow.bancowback.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final long MAX_AGE_SECS = 3600;

	@Value("${url.main}")
	private String main;

	@Value("${url.admin}")
	private String admin;

	@Value("${url.client}")
	private String client;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins(client, admin, main)
			.allowedMethods("GET","POST", "PUT","DELETE","PATCH")
			.allowedHeaders("*")
			.allowCredentials(true)
			.maxAge(MAX_AGE_SECS);
	}
}