package com.aaruu.ems.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aaruu.ems.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(

			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain

	)

			throws ServletException, IOException {

		System.out.println("FILTER ENTERED");

		String token = request.getHeader("Authorization");

		System.out.println("TOKEN -> " + token);

		if (

		token != null

				&&

				token.startsWith("Bearer ")

		) {

			System.out.println("TOKEN FOUND");

			token = token.substring(7);

			String username = jwtService.extractUsername(token);

			System.out.println("USER -> " + username);

		}

		filterChain.doFilter(request, response);

	}

}
