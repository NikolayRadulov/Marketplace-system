package com.app.market.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reports")
public class Report extends BaseEntity {

	@Column(nullable = false)
	private String reportText;
	
	@OneToOne
	private User reportedUser;
	
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

}
