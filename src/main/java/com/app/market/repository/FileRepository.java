package com.app.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.market.model.entity.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
