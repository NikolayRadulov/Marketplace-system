package com.app.market.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

	@Column(nullable = false, unique = true)
	private String name;
	
	@OneToMany(mappedBy = "category")
	private List<Ad> ads;
	
	public Category(String name) {
		this.name = name;
	}

	public Category() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ad> getAds() {
		return ads;
	}

	public void setAds(List<Ad> ads) {
		this.ads = ads;
	}

}
