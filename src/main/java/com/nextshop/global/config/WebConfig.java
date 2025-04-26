package com.nextshop.global.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nextshop.global.interceptor.AuthorizationInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final AuthorizationInterceptor authorizationInterceptor;

	private final List<String> DEFAULT_EXCLUDE = List.of(
		"/**",
		"/error"
	);

	private final List<String> SWAGGER = List.of(
		"/swagger-ui.html",
		"/swagger-ui/**"
	);

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authorizationInterceptor)
			.excludePathPatterns(DEFAULT_EXCLUDE)
			.excludePathPatterns(SWAGGER);
	}
}
