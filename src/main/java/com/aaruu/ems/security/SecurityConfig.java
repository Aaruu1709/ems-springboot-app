package com.aaruu.ems.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aaruu.ems.filter.JwtFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http

				.csrf(csrf -> csrf.disable())

				.authorizeHttpRequests(

						auth -> auth

								.requestMatchers(

										"/swagger-ui/**",

										"/swagger-ui.html",

										"/v3/api-docs/**",

										"/auth/**"

								)

								.permitAll()

								.anyRequest()

								.authenticated()

				);

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();

	}

}
