package com.app.market.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(nullable = false, name = "created_on")
	private LocalDateTime createdOn;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<UserRole> roles;
	
	@OneToMany(mappedBy = "owner")
	private List<Ad> ads;
	
	@OneToMany(mappedBy = "user")
	private List<Rating> ratings;
	
	@OneToOne
	private FileEntity profilePicture;
	
	public User(String username, String email, String phoneNumber, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.createdOn = LocalDateTime.now();
		this.roles = new ArrayList<>();
		this.ads = new ArrayList<>();
		this.ratings = new ArrayList<>();
	}

	public User() {
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

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Ad> getAds() {
		return ads;
	}

	public void setAds(List<Ad> ads) {
		this.ads = ads;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public void addNewRating(Rating rating) {
		this.ratings.add(rating);
	}
	
	public List<Rating> getRatings() {
		return Collections.unmodifiableList(ratings);
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public FileEntity getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(FileEntity profilePicture) {
		this.profilePicture = profilePicture;
	}

}
