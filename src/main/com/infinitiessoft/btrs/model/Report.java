package com.infinitiessoft.btrs.model;
// Generated Jul 9, 2012 10:51:06 AM by Hibernate Tools 3.2.4.GA

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	private Set<StatusChange> statusChanges = new HashSet<StatusChange>(0);
	private List<Expense> expenses = new ArrayList<Expense>(0);

	public Report() {
	}

	public Report(Long id, String reason, String route, Date startDate,
			Date endDate, Date createdDate, Date lastUpdatedDate) {
		this.id = id;
		this.reason = reason;
		this.route = route;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public Report(Long id, User owner, User reviewer,
			String reason, String route, Date startDate, Date endDate,
			String comment, Date createdDate, Date lastUpdatedDate,
			Set<StatusChange> statusChanges, List<Expense> expenses) {
		this.id = id;
		this.owner = owner;
		this.reviewer = reviewer;
		this.reason = reason;
		this.route = route;
		this.startDate = startDate;
		this.endDate = endDate;
		this.comment = comment;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.statusChanges = statusChanges;
		this.expenses = expenses;
	}

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
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", nullable = false, length = 13)
	@NotNull
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "report")
	public Set<StatusChange> getStatusChanges() {
		return this.statusChanges;
	}

	public void setStatusChanges(Set<StatusChange> statusChanges) {
		this.statusChanges = statusChanges;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "report", cascade = CascadeType.ALL)
	public List<Expense> getExpenses() {
		return this.expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
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
			totalTax += expense.getTax();
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
