package com.nextshop.api.token.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nextshop.api.token.dto.AccessTokenResponseDto;
import com.nextshop.domain.admin.entity.Admin;
import com.nextshop.domain.admin.sevice.AdminService;
import com.nextshop.global.jwt.constant.GrantType;
import com.nextshop.global.jwt.service.TokenManager;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {

	private final TokenManager tokenManager;
	private final AdminService adminService;

	public AccessTokenResponseDto createAccessTokenByRefreshToken(String refreshToken) {
		Admin admin = adminService.findMemberByRefreshToken(refreshToken);

		Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
		String accessToken = tokenManager.createAccessToken(admin.getEmail(), accessTokenExpireTime);

		return AccessTokenResponseDto.builder()
			.grantType(GrantType.BEARER.getType())
			.accessToken(accessToken)
			.accessTokenExpireTime(accessTokenExpireTime)
			.build();
	}

}
