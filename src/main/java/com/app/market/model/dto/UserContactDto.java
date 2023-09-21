package com.app.market.model.dto;

public class UserContactDto {

	private String email;
	private String phone;
	
	public UserContactDto(String email, String phone) {
		this.email = email;
		this.phone = phone;
	}
	
	public UserContactDto() {
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
