package com.app.market.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ratings")
public class Rating extends BaseEntity {

	@Column(nullable = false)
	private double score;
	
	@Column
	private String comment;
	
	@ManyToMany
	private List<User> users;
	
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

}
