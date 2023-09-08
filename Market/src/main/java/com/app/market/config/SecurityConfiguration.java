package com.app.market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

	public SecurityConfiguration() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests()
		.requestMatchers("/", "register").permitAll()
		.anyRequest().authenticated();
		
		return httpSecurity.build();
	}

}
