package com.app.market.model.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ImportAdDto {

	@Size(min = 4, max = 50, message = "Title must be between 4 and 50 characters")
	private String title;

	@NotNull
	private String category;
	
	@NotNull
	private MultipartFile image;
	
	@Positive(message = "Price must be positive")
	private double price;
	
	@Size(min = 30, max = 3000, message = "Description must be between 30 and 3000 characters")
	private String description;
	
	@Size(min = 4, max = 50, message = "City must be between 4 and 50 characters")
	private String city;
	
	@Size(min = 6, max = 50, message = "Address must be between 6 and 50 characters")
	private String address;
	
	private String cityZone;	
	
	public ImportAdDto() {
		// TODO Auto-generated constructor stub
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public MultipartFile getImage() {
		return image;
	}


	public void setImage(MultipartFile image) {
		this.image = image;
	}



	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
