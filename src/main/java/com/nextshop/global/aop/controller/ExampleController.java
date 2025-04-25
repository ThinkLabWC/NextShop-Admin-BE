package com.nextshop.global.aop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nextshop.global.aop.annotation.HeaderValidation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api")
@RestController
public class ExampleController {

	@HeaderValidation
	@GetMapping("/hello")
	public void hello(@RequestHeader("X-API-KEY") String apiKey) {

	}

	private boolean validation(String key) {
		return key == null || key.isEmpty();
	}
}
