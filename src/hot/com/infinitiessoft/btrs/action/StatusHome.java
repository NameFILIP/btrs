package com.infinitiessoft.btrs.action;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import com.infinitiessoft.btrs.model.Status;
import com.infinitiessoft.btrs.model.StatusChange;

@Name("statusHome")
public class StatusHome extends EntityHome<Status> {

	private static final long serialVersionUID = -2982920054691381575L;

	public void setStatusId(Long id) {
		setId(id);
	}

	public Long getStatusId() {
		return (Long) getId();
	}

	@Override
	protected Status createInstance() {
		Status status = new Status();
		return status;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Status getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<StatusChange> getStatusChanges() {
		return getInstance() == null ? null : new ArrayList<StatusChange>(
				getInstance().getStatusChanges());
	}

	public String remove(Status status) {
		setInstance(status);
		return remove();
	}
	
}
