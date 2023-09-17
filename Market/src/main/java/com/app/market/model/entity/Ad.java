package com.app.market.model.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	
	@ManyToOne
	private User owner;
	
	@OneToMany(mappedBy = "ad")
	private List<FileEntity> images;
	
	public Ad(String name, BigDecimal price, String description) {
		this.name = name;
		this.price = price;
		this.description = description;
	}

	@ManyToMany(mappedBy = "ads")
	private List<Rating> ratings;
	
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

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public List<FileEntity> getImages() {
		return images;
	}

	public void setImages(List<FileEntity> images) {
		this.images = images;
	}

	public Category getCategory() {
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

}
