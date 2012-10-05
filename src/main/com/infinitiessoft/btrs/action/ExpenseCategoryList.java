package com.infinitiessoft.btrs.action;

import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import com.infinitiessoft.btrs.model.ExpenseCategory;

@Name("expenseCategoryList")
public class ExpenseCategoryList extends EntityQuery<ExpenseCategory> {

	private static final long serialVersionUID = 6507717744406779457L;
	
	List<ExpenseCategory> allCategories = null;

	private static final String EJBQL = "select expenseCategory from ExpenseCategory expenseCategory";

	private static final String[] RESTRICTIONS = {};

	private ExpenseCategory expenseCategory = new ExpenseCategory();

	public ExpenseCategoryList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
		setOrder("code");
	}

	public ExpenseCategory getExpenseCategory() {
		return expenseCategory;
	}
	
	@SuppressWarnings("unchecked")
	public List<ExpenseCategory> getAllCategories() {
		if (allCategories == null) {
			allCategories = getEntityManager().createQuery("select distinct ec from ExpenseCategory ec join fetch ec.expenseTypes et").getResultList();
		}
		return allCategories;
	}
}
