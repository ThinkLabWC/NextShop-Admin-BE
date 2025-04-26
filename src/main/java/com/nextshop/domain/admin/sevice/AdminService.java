package com.nextshop.domain.admin.sevice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nextshop.domain.admin.entity.Admin;
import com.nextshop.domain.admin.repository.AdminRepository;
import com.nextshop.global.error.ErrorCode;
import com.nextshop.global.error.exception.AuthenticationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminRepository adminRepository;

	@Transactional(readOnly = true)
	public void findByEmailAndPassword(String email, String password) {
		adminRepository.findByEmailAndPassword(email, password)
			.orElseThrow(NoSuchElementException::new);
	}

	@Transactional(readOnly = true)
	public Admin findMemberByRefreshToken(String refreshToken) {
		Admin admin = adminRepository.findByRefreshToken(refreshToken)
			.orElseThrow(() -> new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
		LocalDateTime tokenExpirationTime = admin.getTokenExpirationTime();
		if (tokenExpirationTime.isBefore(LocalDateTime.now())) {
			throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
		}
		return admin;
	}
}
