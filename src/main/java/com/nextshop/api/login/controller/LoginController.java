package com.nextshop.api.login.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nextshop.api.login.dto.LoginDto;
import com.nextshop.api.login.service.LoginService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@PostMapping("/login")
	public void login(
		@RequestBody final LoginDto.Request request
	) {
		loginService.login(request);
	}
}
