package com.app.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.market.model.entity.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long>{

	@Query("SELECT a from Ad a JOIN a.category c WHERE c.name = :category")
	List<Ad> findByCategory(String category);
}
