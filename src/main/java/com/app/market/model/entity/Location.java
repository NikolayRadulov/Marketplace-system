package com.app.market.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "locations")
public class Location extends BaseEntity {
	
	@Column(nullable = false)
	private String city;
	
	@Column(nullable = false)
	private String address;
	
	@Column(name = "city_zone")
	private String cityZone;
	
	@OneToMany
	private List<Ad> ads;

	public Location(String city, String address, String cityZone) {
		this.city = city;
		this.address = address;
		this.cityZone = cityZone;
	}

	public Location() {
		// TODO Auto-generated constructor stub
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCityZone() {
		return cityZone;
	}

	public void setCityZone(String cityZone) {
		this.cityZone = cityZone;
	}

	public List<Ad> getAds() {
		return ads;
	}

	public void setAds(List<Ad> ads) {
		this.ads = ads;
	}

}
