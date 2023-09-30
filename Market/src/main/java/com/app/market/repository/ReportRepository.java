package com.app.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.market.model.entity.Report;
import com.app.market.model.entity.User;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
	
	List<Report> findByReportedUser(User reported);
	
	Report findByReportedUserAndReporterUser(User reported, User reporter);
}
