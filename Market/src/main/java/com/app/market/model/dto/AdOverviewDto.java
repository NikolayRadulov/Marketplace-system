package com.app.market.model.dto;

public class AdOverviewDto {

	private long id;
	private String title;
	private String location;
	private double price;
	
	public AdOverviewDto(long id, String title, String location, double price) {
		this.id = id; 
		this.title = title;
		this.location = location;
		this.price = price;
	}

	public AdOverviewDto() {
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
