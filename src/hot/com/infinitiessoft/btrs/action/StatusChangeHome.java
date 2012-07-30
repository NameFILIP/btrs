package com.infinitiessoft.btrs.action;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import com.infinitiessoft.btrs.model.Report;
import com.infinitiessoft.btrs.model.Status;
import com.infinitiessoft.btrs.model.StatusChange;
import com.infinitiessoft.btrs.model.User;

@Name("statusChangeHome")
public class StatusChangeHome extends EntityHome<StatusChange> {

	private static final long serialVersionUID = -4413933617845205283L;
	
	@In(create = true)
	ReportHome reportHome;
	@In(create = true)
	StatusHome statusHome;
	@In(create = true)
	UserHome userHome;

	public void setStatusChangeId(Long id) {
		setId(id);
	}

	public Long getStatusChangeId() {
		return (Long) getId();
	}

	@Override
	protected StatusChange createInstance() {
		StatusChange statusChange = new StatusChange();
		return statusChange;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Report report = reportHome.getDefinedInstance();
		if (report != null) {
			getInstance().setReport(report);
		}
		Status status = statusHome.getDefinedInstance();
		if (status != null) {
			getInstance().setStatus(status);
		}
		User user = userHome.getDefinedInstance();
		if (user != null) {
			getInstance().setUser(user);
		}
	}

	public boolean isWired() {
		if (getInstance().getStatus() == null)
			return false;
		return true;
	}

	public StatusChange getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
