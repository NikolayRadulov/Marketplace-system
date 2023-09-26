package com.app.market.model.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ImportRatingDto {

	@NotNull(message = "Score can not be blank")
	@DecimalMin("1.0")
	@DecimalMax("5.0")
	private double score;
	
	@Size(max = 500, message = "Comment must not be larger than 500 symbols")
	private String comment;
	
	public ImportRatingDto() {
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
