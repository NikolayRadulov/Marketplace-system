package com.app.market.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.market.model.dto.ImportRatingDto;
import com.app.market.model.entity.Rating;
import com.app.market.model.entity.User;
import com.app.market.repository.RatingRepository;
import com.app.market.repository.UserRepository;
import com.app.market.service.impl.RatingServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class RatingServiceTests {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RatingRepository ratingRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	private RatingServiceImpl toTest;
	
	@BeforeEach
	public void setUp() {
		toTest = new RatingServiceImpl(userRepository, ratingRepository, modelMapper);
	}
	
	@Test
	public void testAddNewUserRatingValid() {
		//Arrange
		User ratingUser = Mockito.mock(User.class);
		User ratedUser = Mockito.mock(User.class);
		ImportRatingDto importRatingDto = Mockito.mock(ImportRatingDto.class);
		Rating rating = Mockito.mock(Rating.class);
		
		UserDetails userDetails = Mockito.mock(org.springframework.security.core.userdetails.User.class);
		
		Mockito.when(userRepository.findById((long)1)).thenReturn(Optional.of(ratedUser));
		Mockito.when(userDetails.getUsername()).thenReturn("Ivan");
		Mockito.when(userRepository.findByUsername("Ivan")).thenReturn(ratingUser);
		Mockito.when(ratingRepository.findByRatingUserAndRatedUser(ratingUser, ratedUser)).thenReturn(null);
		Mockito.when(modelMapper.map(importRatingDto, Rating.class)).thenReturn(rating);
		//Act
		toTest.addNewUserRaiting(1, importRatingDto, userDetails);
		
		//Assert
		
		Mockito.verify(modelMapper).map(importRatingDto, Rating.class);
		Mockito.verify(rating).setRatingUser(ratingUser);
		Mockito.verify(rating).setRatedUser(ratedUser);
		Mockito.verify(ratingRepository).save(any());
	}
	
	@Test
	public void testAddNewUserRatingInvalid() {
		//Arrange
		User ratingUser = Mockito.mock(User.class);
		User ratedUser = Mockito.mock(User.class);
		ImportRatingDto importRatingDto = Mockito.mock(ImportRatingDto.class);
		
		UserDetails userDetails = Mockito.mock(org.springframework.security.core.userdetails.User.class);
		
		Mockito.when(userRepository.findById((long)1)).thenReturn(Optional.of(ratedUser));
		Mockito.when(userDetails.getUsername()).thenReturn("Ivan");
		Mockito.when(userRepository.findByUsername("Ivan")).thenReturn(ratingUser);
		Mockito.when(ratingRepository.findByRatingUserAndRatedUser(ratingUser, ratedUser)).thenReturn(Mockito.mock(Rating.class));
		//Act
		toTest.addNewUserRaiting(1, importRatingDto, userDetails);
		
		//Assert
		
		Mockito.verify(ratingRepository, times(0)).save(any());
		Mockito.verifyNoInteractions(modelMapper);
	}
}
