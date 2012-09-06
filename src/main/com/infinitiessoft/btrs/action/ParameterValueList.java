package com.infinitiessoft.btrs.action;

import java.util.Arrays;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import com.infinitiessoft.btrs.model.ParameterValue;

@Name("parameterValueList")
public class ParameterValueList extends EntityQuery<ParameterValue> {

	private static final long serialVersionUID = 652655803549280703L;

	private static final String EJBQL = "select parameterValue from ParameterValue parameterValue";

	private static final String[] RESTRICTIONS = {};

	private ParameterValue parameterValue = new ParameterValue();

	public ParameterValueList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public ParameterValue getParameterValue() {
		return parameterValue;
	}
}
