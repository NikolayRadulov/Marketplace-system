package com.app.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.market.model.entity.Rating;
import com.app.market.model.entity.User;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>{

	List<Rating> findByRatedUser(User user);
	
	Rating findByRatingUserAndRatedUser(User rated, User raiting);
}
