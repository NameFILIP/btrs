package com.infinitiessoft.btrs.action;

import java.util.Arrays;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import com.infinitiessoft.btrs.model.Expense;

@Name("expenseList")
public class ExpenseList extends EntityQuery<Expense> {

	private static final long serialVersionUID = -5106562037512756948L;

	private static final String EJBQL = "select expense from Expense expense";

	private static final String[] RESTRICTIONS = {};

	private Expense expense = new Expense();

	public ExpenseList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Expense getExpense() {
		return expense;
	}
}
