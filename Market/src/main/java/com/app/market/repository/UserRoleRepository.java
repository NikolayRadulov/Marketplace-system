package com.app.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.market.model.entity.UserRole;
import com.app.market.model.enums.UserRoleEnum;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	UserRole findByUserRole(UserRoleEnum role);
}
