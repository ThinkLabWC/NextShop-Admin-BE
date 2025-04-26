package com.nextshop.api.login.service;

import org.springframework.stereotype.Service;

import com.nextshop.api.login.dto.LoginDto;
import com.nextshop.domain.admin.sevice.AdminService;
import com.nextshop.global.jwt.dto.JwtTokenDto;
import com.nextshop.global.jwt.service.TokenManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
	private final AdminService adminService;
	private final TokenManager tokenManager;

	public JwtTokenDto login(final LoginDto.Request request) {
		adminService.findByEmailAndPassword(request.email(), request.password());
		return tokenManager.createJwtTokenDto(request.email());
	}
}
