package com.nextshop.domain.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nextshop.domain.admin.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	Optional<Admin> findByEmailAndPassword(String email, String password);

	Optional<Admin> findByRefreshToken(String refreshToken);
}
