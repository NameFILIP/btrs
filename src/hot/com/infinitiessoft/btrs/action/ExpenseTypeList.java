package com.infinitiessoft.btrs.action;

import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.model.ExpenseType;

@Name("expenseTypeList")
public class ExpenseTypeList extends EntityQuery<ExpenseType> {

	@Logger Log log;
	
	private static final long serialVersionUID = -1508828451706319531L;

	private static final String EJBQL = "select expenseType from ExpenseType expenseType";

	private static final String[] RESTRICTIONS = {};

	private ExpenseType expenseType = new ExpenseType();
	
	public ExpenseTypeList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}
	
	public List<ExpenseType> loadByCategory() {
		String[] restrictions = {"expenseType.expenseCategory.id = #{expenseCategory.id}"};
		setRestrictionExpressionStrings(Arrays.asList(restrictions));
		return getResultList();
	}
}
	
