package com.app.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.market.model.entity.BannedUser;

@Repository
public interface BannedUserRepository extends JpaRepository<BannedUser, Long> {

	BannedUser findByUsername(String username);
}
