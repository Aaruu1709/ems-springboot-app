package com.aaruu.ems.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	public static final String SECRET = "mySecretKeyForEmployeeManagementProject123456789";
	private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

	public String generateToken(String username, String role) {

		return Jwts.builder()

				.subject(username).claim("role", role)

				.issuedAt(new Date())

				.expiration(

						new Date(

								System.currentTimeMillis()

										+ 1000 * 60 * 60

						)

				)

				.signWith(key)

				.compact();

	}

	public String extractUsername(String token) {

		return Jwts.parser()

				.verifyWith((javax.crypto.SecretKey) key)

				.build()

				.parseSignedClaims(token)

				.getPayload()

				.getSubject();

	}

	// Extract Role From JWT
	public String extractRole(String token) {
		return Jwts.parser().verifyWith((javax.crypto.SecretKey) key).build().parseSignedClaims(token).getPayload()
				.get("role", String.class);
	}

}
