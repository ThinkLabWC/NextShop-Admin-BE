package com.nextshop.global.aop.aspects;

import static io.micrometer.common.util.StringUtils.*;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nextshop.global.aop.annotation.HeaderValidation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class HeaderValidationAspect {

	@Value("${app.required-header.api-key}")
	boolean key;

	@Before("@annotation(headerValidation)")
	public void validateHeader(HeaderValidation headerValidation) {
		if (key) {
			ServletRequestAttributes requestAttributes =
				(ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = requestAttributes.getRequest();

			// request header에 있는 토큰 체크하기
			String token = request.getHeader("X-API-KEY");
			if (isBlank(token)) {
				throw new IllegalArgumentException("X-API-KEY is empty");
			}
		}
	}
}
