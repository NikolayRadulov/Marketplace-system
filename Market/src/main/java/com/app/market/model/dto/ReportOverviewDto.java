package com.app.market.model.dto;

public class ReportOverviewDto {

	private long id;
	private long reportedUserId;
	private String reportMessage;
	
	public ReportOverviewDto(long id, long reportedUserId, String reportMessage) {
		this.id = id;
		this.reportedUserId = reportedUserId;
		this.reportMessage = reportMessage;
	}

	public ReportOverviewDto() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getReportedUserId() {
		return reportedUserId;
	}

	public void setReportedUserId(long reportedUserId) {
		this.reportedUserId = reportedUserId;
	}

	public String getReportMessage() {
		return reportMessage;
	}

	public void setReportMessage(String reportMessage) {
		this.reportMessage = reportMessage;
	}

}
