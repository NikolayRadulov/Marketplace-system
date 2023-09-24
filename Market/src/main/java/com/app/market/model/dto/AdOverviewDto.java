package com.app.market.model.dto;

public class AdOverviewDto {

	private long id;
	private String title;
	private String location;
	private String date;
	private double price;
	private String description;
	
	public AdOverviewDto(long id, String title, String location, double price, String description) {
		this.id = id; 
		this.title = title;
		this.location = location;
		this.price = price;
		this.description = description;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
