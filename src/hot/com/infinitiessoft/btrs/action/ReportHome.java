package com.infinitiessoft.btrs.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.enums.StatusEnum;
import com.infinitiessoft.btrs.model.Expense;
import com.infinitiessoft.btrs.model.Report;
import com.infinitiessoft.btrs.model.StatusChange;
import com.infinitiessoft.btrs.model.User;

@Name("reportHome")
public class ReportHome extends EntityHome<Report> {

	private static final long serialVersionUID = -2118409698027259254L;
	
	@Logger Log log;
	
	@In
	User currentUser;
	
	private String actionName;
	private String comment;

	public void setReportId(Long id) {
		setId(id);
	}

	public Long getReportId() {
		return (Long) getId();
	}

	@Override
	protected Report createInstance() {
		Report report = new Report();
		report.setId(System.currentTimeMillis());
		report.setOwner(currentUser);
		return report;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
//		getInstance();
//		User user = userHome.getDefinedInstance();
//		if (user != null) {
//			getInstance().setUser(user);
//		}
	}

	public boolean isWired() {
		return true;
	}

	public Report getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Expense> getExpenses() {
		return getInstance() == null ? null : getInstance().getExpenses();
	}
	public List<StatusChange> getStatusChanges() {
		return getInstance() == null ? null : new ArrayList<StatusChange>(
				getInstance().getStatusChanges());
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String persist() {
		Report report = getInstance();
		report.setCreatedDate(new Date());
		report.setCurrentStatus(StatusEnum.SUBMITTED);
		return super.persist();
	}
	
//	public String generatorPersist() {
//		return super.persist();
//	}

	@Override
	public String update() {
		Report report = getInstance();
		report.setLastUpdatedDate(new Date());
		if ((report.getCurrentStatus() == StatusEnum.REJECTED || report.getCurrentStatus() == StatusEnum.APPROVED)
				&& currentUser.equals(report.getOwner()) && ! currentUser.equals(report.getReviewer())) {
			report.setCurrentStatus(StatusEnum.SUBMITTED);
		}
		return super.update();
	}
	
	public String approve(Long reportId) {
		setReportId(reportId);
		return approve();
	}
	
	public String reject(Long reportId) {
		setReportId(reportId);
		return reject();
	}
	
	public String approve() {
		changeStatus(StatusEnum.APPROVED);
		return "approved";
	}
	
	public String reject() {
		changeStatus(StatusEnum.REJECTED);
		return "rejected";
	}
	
	private void changeStatus(StatusEnum status) {
		Report report = getInstance();
		
		log.debug("Changing status of Report({0}) to {1}, with comment {2}", report, status, comment);
		
		StatusChange statusChange = new StatusChange(currentUser, status, report, comment, new Date());
		report.addStatusChange(statusChange);
		report.setCurrentStatus(status);
		update();
	}
	
}
