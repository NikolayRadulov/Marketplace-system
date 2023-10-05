package com.app.market.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ads")
public class Ad extends BaseEntity {

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;
	
	@Column(nullable = false, name = "posted_on")
	private LocalDateTime postedOn;
	
	@ManyToOne
	private User owner;
	
	@OneToOne(mappedBy = "ad")
	private FileEntity image;
	
	public Ad(String name, BigDecimal price, String description) {
		this.name = name;
		this.price = price;
		this.description = description;
	}
	
	@ManyToOne
	private Category category;
	
	@OneToOne
	private Location location;
	
	public Ad() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public FileEntity getImage() {
		return image;
	}

	public void setImage(FileEntity images) {
		this.image = images;
	}

	public Category getCategory() 	{
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public LocalDateTime getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(LocalDateTime postedOn) {
		this.postedOn = postedOn;
	}

}
