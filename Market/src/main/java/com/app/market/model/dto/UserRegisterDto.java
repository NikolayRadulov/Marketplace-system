package com.app.market.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegisterDto {

	@NotBlank
	@Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
	@Pattern(regexp = "[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*", message = "Username must only contain letters, digits, underscore or dot. It can not begin or end with dot or underscore!")
	private String username;
	
	@NotBlank
	@Email(message = "Must be a valid email!")
	@Size(min = 6, max = 30, message = "Email must be between 6 and 30 charecters")
	private String email;
	
	@NotBlank
	@Size(min = 10, max = 10, message = "Invalid phone number")
	private String phoneNumber;
	
	@NotBlank
	@Size(min = 8, max = 25, message = "Password must be between 6 and 25 characters")
	@Pattern(regexp = "^(?=(.*[a-z]){3,})(?=(.*[A-Z]){2,})(?=(.*[0-9]){2,})(?=(.*[!@#$%^&*()\\-__+.]){1,}).{8,}$", message = "The password must contain at least 2 upper case letters, 1 special character and 2 numerals!")
	private String password;
	
	@NotBlank
	@Size(min = 8, max = 25, message = "Password must be between 6 and 25 characters")
	private String confirmPassword;
	
	public UserRegisterDto() {
		// TODO Auto-generated constructor stub
	}
	
	

	public UserRegisterDto(
			@NotBlank @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters") @Pattern(regexp = "[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*", message = "Username must only contain letters, digits, underscore or dot. It can not begin or end with dot or underscore!") String username,
			@NotBlank @Email @Size(min = 9, max = 30, message = "Email must be between 9 and 30 charecters") String email,
			@NotBlank @Size(min = 10, max = 10, message = "Invalid phone number") String phoneNumber,
			@NotBlank @Size(min = 6, max = 25, message = "Password must be between 6 and 25 characters") String password,
			@NotBlank @Size(min = 6, max = 25) String confirmPassword) {
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.confirmPassword = confirmPassword;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


}
