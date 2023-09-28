package com.app.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.market.model.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {


}