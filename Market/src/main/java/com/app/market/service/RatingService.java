package com.app.market.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.app.market.model.dto.ImportRatingDto;

public interface RatingService {

	boolean addNewUserRaiting(long userId, ImportRatingDto importRatingDto, UserDetails sender);
}
