package com.nextshop.global.aop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nextshop.global.aop.annotation.HeaderValidation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api")
@RestController
public class ExampleController {

	@HeaderValidation(key = "X-API-KEY")
	@GetMapping("/hello")
	public void hello() {

	}

	private boolean validation(String key) {
		return key == null || key.isEmpty();
	}
}
