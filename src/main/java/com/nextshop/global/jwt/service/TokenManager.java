package com.nextshop.global.jwt.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import com.nextshop.global.error.ErrorCode;
import com.nextshop.global.error.exception.AuthenticationException;
import com.nextshop.global.jwt.constant.GrantType;
import com.nextshop.global.jwt.constant.TokenType;
import com.nextshop.global.jwt.dto.JwtTokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TokenManager {

	private final String accessTokenExpirationTime;
	private final String refreshTokenExpirationTime;
	private final String tokenSecret;

	public JwtTokenDto createJwtTokenDto(String email) {
		Date accessTokenExpireTime = createAccessTokenExpireTime();
		Date refreshTokenExpireTime = createRefreshTokenExpireTime();

		String accessToken = createAccessToken(email, accessTokenExpireTime);
		String refreshToken = createRefreshToken(email, refreshTokenExpireTime);
		return JwtTokenDto.builder()
			.grantType(GrantType.BEARER.getType())
			.accessToken(accessToken)
			.accessTokenExpireTime(accessTokenExpireTime)
			.refreshToken(refreshToken)
			.refreshTokenExpireTime(refreshTokenExpireTime)
			.build();
	}

	public Date createAccessTokenExpireTime() {
		return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
	}

	public Date createRefreshTokenExpireTime() {
		return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
	}

	public String createAccessToken(String email, Date expirationTime) {
		String accessToken = Jwts.builder()
			.setSubject(TokenType.ACCESS.name())    // 토큰 제목
			.setIssuedAt(new Date())                // 토큰 발급 시간
			.setExpiration(expirationTime)          // 토큰 만료 시간
			.claim("email", email)      // 회원 이메일
			.signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
			.setHeaderParam("typ", "JWT")
			.compact();
		return accessToken;
	}

	public String createRefreshToken(String email, Date expirationTime) {
		String refreshToken = Jwts.builder()
			.setSubject(TokenType.REFRESH.name())   // 토큰 제목
			.setIssuedAt(new Date())                // 토큰 발급 시간
			.setExpiration(expirationTime)          // 토큰 만료 시간
			.claim("email", email)      // 회원 이메일
			.signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
			.setHeaderParam("typ", "JWT")
			.compact();
		return refreshToken;
	}

	public void validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
				.parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			log.info("token 만료", e);
			throw new AuthenticationException(ErrorCode.TOKEN_EXPIRED);
		} catch (Exception e) {
			log.info("유효하지 않은 token", e);
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		}
	}

	public Claims getTokenClaims(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
				.parseClaimsJws(token).getBody();
		} catch (Exception e) {
			log.info("유효하지 않은 token", e);
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		}
		return claims;
	}

}
