package com.infinitiessoft.btrs.attendance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AttendanceRecord {
	
	private static final String OUT_DATE_FORMAT = "dd/MM/yyyy";
	private static DateFormat df = new SimpleDateFormat(OUT_DATE_FORMAT);
	
	private Long id;
	private Date bookDate;
	private Date startDate;
	private Date endDate;
	private String type;
	private String reason;
	private String employeeName;
	private Long employeeId;


	public AttendanceRecord() {	}
	
	public AttendanceRecord(Long id, Date bookDate, Date startDate, Date endDate, String type, String reason,
			String employeeName, Long employeeId) {
		this.id = id;
		this.bookDate = bookDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.reason = reason;
		this.employeeName = employeeName;
		this.employeeId = employeeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
	public String getLabel() {
		return df.format(startDate) + " - " + df.format(endDate) + " " + type;
	}
}