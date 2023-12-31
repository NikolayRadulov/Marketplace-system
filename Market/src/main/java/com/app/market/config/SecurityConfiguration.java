package com.app.market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import com.app.market.model.enums.UserRoleEnum;
import com.app.market.repository.UserRepository;
import com.app.market.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity, SecurityContextRepository securityContextRepository) throws Exception {
		
		httpSecurity.authorizeHttpRequests((requests) -> 
			requests.requestMatchers("/", "/users/register", "/users/login", "/static/**", "styles/**", "js/**", "categoryImages/**", "/categories/getAll", "/error", "/users/loadUsers", "/users/logout", "/users/login-error").permitAll()
			.requestMatchers("/users/admins", "/users/changeRole").hasAuthority(UserRoleEnum.ADMIN.name())
			.requestMatchers("/users/moderators", "/users/banUser/{id}", "/reports/**").hasAuthority(UserRoleEnum.MODERATOR.name())
			.anyRequest().authenticated())
		.formLogin((form) -> 
		form.loginPage("/users/login")
		.usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
		.passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
		.defaultSuccessUrl("/", true)
		.failureForwardUrl("/users/login-error"))
		.logout((logout) -> 
		logout.logoutUrl("/users/logout")
		.logoutSuccessUrl("/"))
		.securityContext((securityContext) -> 
		securityContext.securityContextRepository(securityContextRepository));
		
		return httpSecurity.build();
	}
	
	@Bean 
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	} 
	
	
	@Bean 
	public SecurityContextRepository getSecurityContextRepository() {
		return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository());
	}
	
	@Bean
	public UserDetailsService getUserDetailsService(UserRepository userRepository) {
		return new UserDetailsServiceImpl(userRepository);
	}

}
