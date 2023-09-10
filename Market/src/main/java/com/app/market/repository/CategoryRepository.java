package com.app.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.market.model.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
