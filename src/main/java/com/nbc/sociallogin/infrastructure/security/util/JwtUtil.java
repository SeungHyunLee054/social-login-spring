package com.nbc.sociallogin.infrastructure.security.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nbc.sociallogin.domain.auth.vo.AuthUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtil {
	private final SecretKey secretKey;

	public JwtUtil(@Value("${jwt.secret.key}") String secretKey) {
		this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	public String generateToken(AuthUser authUser) {
		Date date = new Date();

		return Jwts.builder()
			.subject(authUser.getEmail())
			.id(authUser.getId().toString())
			.claim("userRole", authUser.getUserRole())
			.issuedAt(date)
			.expiration(new Date(date.getTime() + 60 * 60 * 1000L))
			.signWith(secretKey, Jwts.SIG.HS256)
			.compact();
	}

	public Claims parseToken(String token) {
		try {
			return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
		} catch (ExpiredJwtException expiredJwtException) {
			throw new RuntimeException(expiredJwtException.getMessage());
		} catch (MalformedJwtException malformedJwtException) {
			throw new RuntimeException(malformedJwtException.getMessage());
		} catch (SignatureException signatureException) {
			throw new RuntimeException(signatureException.getMessage());
		} catch (UnsupportedJwtException unsupportedJwtException) {
			throw new RuntimeException(unsupportedJwtException.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public boolean isTokenExpired(String token) {
		Claims claims = parseToken(token);

		return claims.getExpiration().before(new Date());
	}
}
