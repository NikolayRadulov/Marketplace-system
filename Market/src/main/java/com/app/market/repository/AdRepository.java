package com.app.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.market.model.entity.Ad;
import com.app.market.model.entity.User;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long>{

	@Query("SELECT a from Ad a JOIN a.category c WHERE c.name = :category")
	List<Ad> findByCategory(String category);
	
	@Query("SELECT a from Ad a JOIN a.category c WHERE c.name = :category and a.price >= :minPrice and a.price <= :maxPrice")
	List<Ad> findByCategoryAndFilters(String category, double minPrice, double maxPrice);
	
	List<Ad> findByOwner(User owner);
	
	List<Ad> findByNameContains(String text);
}
