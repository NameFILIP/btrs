package com.infinitiessoft.btrs.action;

import java.util.Arrays;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import com.infinitiessoft.btrs.model.StatusChange;

@Name("statusChangeList")
public class StatusChangeList extends EntityQuery<StatusChange> {

	private static final long serialVersionUID = -1383492668781672065L;

	private static final String EJBQL = "select statusChange from StatusChange statusChange";

	private static final String[] RESTRICTIONS = {};

	private StatusChange statusChange = new StatusChange();

	public StatusChangeList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public StatusChange getStatusChange() {
		return statusChange;
	}
}
