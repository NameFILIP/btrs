package com.infinitiessoft.btrs.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import com.infinitiessoft.btrs.model.Expense;
import com.infinitiessoft.btrs.model.Report;
import com.infinitiessoft.btrs.model.StatusChange;
import com.infinitiessoft.btrs.model.User;

@Name("reportHome")
public class ReportHome extends EntityHome<Report> {

	private static final long serialVersionUID = -2118409698027259254L;
	
	@In("#{currentUser}")
	User currentUser;
	

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

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
		report.setCreatedDate(new Date());
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
	
	
}
