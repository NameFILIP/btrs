package com.infinitiessoft.btrs.model;
// Generated Jul 9, 2012 10:51:06 AM by Hibernate Tools 3.2.4.GA

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Past;

import com.infinitiessoft.btrs.enums.StatusEnum;

/**
 * Reports generated by hbm2java
 */
@Entity
@Table(name = "reports")
public class Report implements java.io.Serializable {

	private static final long serialVersionUID = -1116817819653771651L;
	
	private Long id;
	
	private User owner;
	private User reviewer;
	
	private String reason;
	private String route;
	private Date startDate;
	private Date endDate;
	private String comment;
	private Date createdDate;
	private Date lastUpdatedDate;
	
	private StatusEnum currentStatus;
	
	private List<StatusChange> statusChanges;
	private List<Expense> expenses;

	
	public Report() {}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	public User getOwner() {
		return this.owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Column(name = "reason", nullable = false, length = 1000)
	@NotNull
	@Length(max = 1000)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "route", nullable = false)
	@NotNull
	public String getRoute() {
		return this.route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date", nullable = false, length = 13)
	@NotNull
	@Past
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", nullable = false, length = 13)
	@NotNull
	@Past
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated_date", length = 29)
	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reviewer_id")
	public User getReviewer() {
		return reviewer;
	}

	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}

	@Transient
	public String getCurrentStatusNameKey() {
		return currentStatus.getLabel();
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "current_status")
	public StatusEnum getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(StatusEnum currentStatus) {
		this.currentStatus = currentStatus;
	}

//	@OrderBy("createdDate")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "report", cascade = CascadeType.ALL)
	public List<StatusChange> getStatusChanges() {
		if (statusChanges == null) {
			statusChanges = new ArrayList<StatusChange>(0);
		}
		return this.statusChanges;
	}

	public void setStatusChanges(List<StatusChange> statusChanges) {
		this.statusChanges = statusChanges;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "report", cascade = CascadeType.ALL)
	public List<Expense> getExpenses() {
		if (expenses == null) {
			expenses = new ArrayList<Expense>(0);
		}
		return this.expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}
	
	
	@Override
	public String toString() {
		return "Report [id=" + id + ", owner=" + owner + ", reviewer=" + reviewer + ", currentStatus=" + currentStatus + "]";
	}

	@Transient
	public boolean addExpense(Expense expense) {
		return getExpenses().add(expense);
	}
	
	@Transient
	public boolean removeExpense(Expense expense) {
		return getExpenses().remove(expense);
	}
	
	@Transient
	public boolean addStatusChange(StatusChange statusChange) {
		return getStatusChanges().add(statusChange);
	}
	
	@Transient
	public Integer getTotalAmount() {
		int total = 0;
		for (Expense expense : expenses) {
			total += expense.getTotalAmount();
		}
		return total;
	}
	
	@Transient
	public Integer getTotalTaxAmount() {
		int totalTax = 0;
		for (Expense expense : expenses) {
			totalTax += expense.getTaxAmount();
		}
		return totalTax;
	}
	
	@Transient
	public Integer getTotalAmountWithoutTax() {
		int totalWithoutTax = 0;
		for (Expense expense : expenses) {
			totalWithoutTax += expense.getAmountWithoutTax();
		}
		return totalWithoutTax;
	}
}
