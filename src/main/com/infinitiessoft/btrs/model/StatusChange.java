package com.infinitiessoft.btrs.model;
// Generated Jul 9, 2012 10:51:06 AM by Hibernate Tools 3.2.4.GA

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.NotNull;

import com.infinitiessoft.btrs.enums.StatusEnum;

/**
 * StatusChanges generated by hbm2java
 */
@Entity
@Table(name = "status_changes")
public class StatusChange implements java.io.Serializable {

	private static final long serialVersionUID = 6722775591220031082L;
	
	private Long id;
	private User user;
	private StatusEnum value;
	private Report report;
	private String comment;
	private Date createdDate;


	public StatusChange() {}

	
	public StatusChange(User user, StatusEnum value, Report report, String comment, Date createdDate) {
		this.user = user;
		this.value = value;
		this.report = report;
		this.comment = comment;
		this.createdDate = createdDate;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "revisor_id")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Transient
	public String getNameKey() {
		return this.value.getLabel();
	}
	
//	@ManyToOne(fetch = FetchType.LAZY)
	@Enumerated(EnumType.STRING)
	@Column(name = "value", nullable = false)
	@NotNull
	public StatusEnum getValue() {
		return this.value;
	}

	public void setValue(StatusEnum value) {
		this.value = value;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "report_id")
	public Report getReport() {
		return this.report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	@Column(length = 4000)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, length = 29)
	@NotNull
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
