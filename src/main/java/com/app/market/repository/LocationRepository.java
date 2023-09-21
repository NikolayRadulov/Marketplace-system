package com.app.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.market.model.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{

}
