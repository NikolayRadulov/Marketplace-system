package com.app.market.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ratings")
public class Rating extends BaseEntity {

	@Column(nullable = false)
	private double score;
	
	@Column
	private String comment;
	
	@ManyToOne
	private User ratedUser;
	
	@ManyToOne
	private User ratingUser;
	
	public Rating(double score, String comment) {
		this.score = score;
		this.comment = comment;
	}

	public Rating() {
		// TODO Auto-generated constructor stub
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getRatedUser() {
		return ratedUser;
	}

	public void setRatedUser(User ratedUser) {
		this.ratedUser = ratedUser;
	}

	public User getRatingUser() {
		return ratingUser;
	}

	public void setRatingUser(User ratingUser) {
		this.ratingUser = ratingUser;
	}

}
