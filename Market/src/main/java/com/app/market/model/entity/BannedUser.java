package com.app.market.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "banned_users")
public class BannedUser extends BaseEntity {

	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private LocalDateTime banExpire;
	
	public BannedUser(String username, LocalDateTime banExpire) {
		this.username = username;
		this.banExpire = banExpire;
	}

	public BannedUser() {
		// TODO Auto-generated constructor stub
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getBanExpire() {
		return banExpire;
	}

	public void setBanExpire(LocalDateTime banExpire) {
		this.banExpire = banExpire;
	}

}
