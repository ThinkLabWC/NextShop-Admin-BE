package com.nextshop.api.login.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nextshop.global.jwt.dto.JwtTokenDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public class LoginDto {
	public record Request(
		@NotBlank
		String email,
		@NotBlank
		String password
	) {
	}

	@Builder
	public record Response(
		@Schema(description = "grantType", example = "Bearer", required = true)
		String grantType,

		@Schema(description = "accessToken", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJpYXQiOjE2NTg0ODAyOTYsImV4cCI6MTY1ODQ4MTE5NiwibWVtYmVySWQiOjEsInJvbGUiOiJBRE1JTiJ9.qr5fOs9NIO5UYJzqgisESOXorASLphj04uyjF1Breolj4cou_k6py0egF8f3OxWjQXps3on7Ko3jwIaL_2voRg", required = true)
		String accessToken,

		@Schema(description = "access token 만료 시간", example = "2022-07-22 18:13:16", required = true)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		Date accessTokenExpireTime,

		@Schema(description = "refreshToken", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSRUZSRVNIIiwiaWF0aASDgwMjk3LCJleHAiOjE2NTk2ODk4OTYsIm1lbWJlcklkIjoxfQ.hxgq_DIU554lUnUCSAGTYOiaXLXwgpyIM2h8a5de3ALEOY-IokElS6VbMmVTKlpRaLfEzzcr3FkUDrNisRt-bA", required = true)
		String refreshToken,

		@Schema(description = "refresh token 만료 시간", example = "2022-08-05 18:13:16", required = true)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		Date refreshTokenExpireTime
	) {
		public static Response of(JwtTokenDto jwtTokenDto) {
			return Response.builder()
				.grantType(jwtTokenDto.getGrantType())
				.accessToken(jwtTokenDto.getAccessToken())
				.accessTokenExpireTime(jwtTokenDto.getAccessTokenExpireTime())
				.refreshToken(jwtTokenDto.getRefreshToken())
				.refreshTokenExpireTime(jwtTokenDto.getRefreshTokenExpireTime())
				.build();
		}
	}
}
