package com.aaruu.ems.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aaruu.ems.filter.JwtFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(

			HttpSecurity http

	)

			throws Exception {

		http

				.csrf(

						csrf -> csrf.disable()

				)

				.authorizeHttpRequests(

						auth -> auth

								.requestMatchers(

										"/auth/**",

										"/swagger-ui/**",

										"/swagger-ui.html",

										"/v3/api-docs/**"

								)

								.permitAll()

								.requestMatchers(

										HttpMethod.GET,

										"/employees/**"

								)

								.hasAnyRole(

										"ADMIN",

										"USER"

								)

								.requestMatchers(

										HttpMethod.POST,

										"/employees/**"

								)

								.hasRole(

										"ADMIN"

								)

								.requestMatchers(

										HttpMethod.PUT,

										"/employees/**"

								)

								.hasRole(

										"ADMIN"

								)

								.requestMatchers(

										HttpMethod.DELETE,

										"/employees/**"

								)

								.hasRole(

										"ADMIN"

								)

								.requestMatchers(

										HttpMethod.PATCH,

										"/employees/**"

								)

								.hasRole(

										"ADMIN"

								)

								.anyRequest()

								.authenticated()

				)

				.sessionManagement(

						session ->

						session.sessionCreationPolicy(

								SessionCreationPolicy.STATELESS

						)

				)

				.addFilterBefore(

						jwtFilter,

						UsernamePasswordAuthenticationFilter.class

				);

		return http.build();

	}

}
