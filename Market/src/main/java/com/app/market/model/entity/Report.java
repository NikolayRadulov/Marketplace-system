package com.app.market.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reports")
public class Report extends BaseEntity {

	@Column(nullable = false)
	private String reportText;
	
	@ManyToOne
	private User reportedUser;
	
	@ManyToOne
	private User reporterUser;
	
	public Report(String reportText) {
		this.reportText = reportText;
	}

	public Report() {
		// TODO Auto-generated constructor stub
	}

	public String getReportText() {
		return reportText;
	}

	public void setReportText(String reportText) {
		this.reportText = reportText;
	}

	public User getReportedUser() {
		return reportedUser;
	}

	public void setReportedUser(User reportedUser) {
		this.reportedUser = reportedUser;
	}

	public User getReporterUser() {
		return reporterUser;
	}

	public void setReporterUser(User reporterUser) {
		this.reporterUser = reporterUser;
	}

}
