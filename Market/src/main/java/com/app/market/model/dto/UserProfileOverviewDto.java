package com.app.market.model.dto;

import java.time.LocalDateTime;

public class UserProfileOverviewDto {

	private long id;
	private String username;
	private String email;
	private String phoneNumber;
	private LocalDateTime createdOn;
	
	
	public UserProfileOverviewDto(long id, String username, String email, String phoneNumber, LocalDateTime createdOn) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.createdOn = createdOn;
	}


	public UserProfileOverviewDto() {
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


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public LocalDateTime getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

}
