package com.infinitiessoft.btrs.action;

import java.util.Arrays;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import com.infinitiessoft.btrs.model.Status;

@Name("statusList")
public class StatusList extends EntityQuery<Status> {

	private static final long serialVersionUID = 8616446053398243092L;

	private static final String EJBQL = "select status from Status status";

	private static final String[] RESTRICTIONS = {"lower(status.name) like lower(concat(#{statusList.status.name},'%'))",};

	private Status status = new Status();

	public StatusList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Status getStatus() {
		return status;
	}
}
