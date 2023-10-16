package com.bankinc.credibanco.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bankinc.credibanco.controller.CardController;
import com.bankinc.credibanco.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	//private final AuthenticationProvider authProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// configuration
		//log.info("entro authProvider: " + authProvider);
		http.authorizeHttpRequests(authRequest -> authRequest
				// .requestMatchers("/v1/**").hasRole("USER")
				.requestMatchers("/auth/**","/v1/**","/v3/api-docs/**","swagger-ui/**","swagger-ui.html").permitAll()
				.anyRequest().authenticated())
				.sessionManagement(
						sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
				// TODO: solucionar bearer token
				http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		http.httpBasic(Customizer.withDefaults());
		http.csrf(csrf -> csrf.disable());
		return http.build();

	}
}