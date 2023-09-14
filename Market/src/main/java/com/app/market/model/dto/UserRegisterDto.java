package com.app.market.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterDto {

	@NotBlank
	@Size(min = 4, max = 20)
	private String username;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Size(min = 6, max = 25)
	private String password;
	
	@NotBlank
	@Size(min = 6, max = 25)
	private String confirmPassword;
	
	public UserRegisterDto() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
