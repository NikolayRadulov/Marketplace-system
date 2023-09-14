package com.app.market.model.entity;

import java.util.List;

import com.app.market.model.enums.UserRoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "user_role", unique = true)
	private UserRoleEnum userRole;
	
	@ManyToMany(mappedBy = "roles")
	private List<User> users;
	
	public UserRole(UserRoleEnum userRole) {
		this.userRole = userRole;
	}

	public UserRole() {
		// TODO Auto-generated constructor stub
	}

	public UserRoleEnum getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRoleEnum userRole) {
		this.userRole = userRole;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
