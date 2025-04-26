package com.nextshop.domain.admin.entity;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity(name = "tb_admin")
@EntityListeners(AuditingEntityListener.class)
public class Admin extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id")
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	private String password;

	private LocalDateTime lastLoginAt;

	private String refreshToken;

	private LocalDateTime tokenExpirationTime;

	private boolean deleted;
}
