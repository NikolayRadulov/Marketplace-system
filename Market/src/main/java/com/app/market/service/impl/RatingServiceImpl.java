package com.app.market.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.app.market.model.dto.ImportRatingDto;
import com.app.market.model.entity.Rating;
import com.app.market.model.entity.User;
import com.app.market.repository.RatingRepository;
import com.app.market.repository.UserRepository;
import com.app.market.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	private final UserRepository userRepository;
	private final RatingRepository ratingRepository;
	private final ModelMapper modelMapper;
	
	public RatingServiceImpl(UserRepository userRepository, RatingRepository ratingRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.ratingRepository = ratingRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public void addNewUserRaiting(long userId, ImportRatingDto importRatingDto, UserDetails userDetails) {
		User user = userRepository.findById(userId).get();
		User sender = userRepository.findByUsername(userDetails.getUsername());
		Rating rating = modelMapper.map(importRatingDto, Rating.class);
		
		rating.setUser(user);
		ratingRepository.save(rating);
		
		user.addNewRating(rating);
		userRepository.save(user);
	}

}
