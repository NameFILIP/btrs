package com.infinitiessoft.btrs.custom;

import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.infinitiessoft.btrs.action.ExpenseCategoryList;
import com.infinitiessoft.btrs.model.ExpenseCategory;

@Name("reportingUtils")
public class ReportingUtils {

	@In(create = true)
	ExpenseCategoryList expenseCategoryList;
	
	public Integer tableColumnsSize() {
		int columnsSize = 2; // Employee, Row Total
		
		List<ExpenseCategory> expenseCategories = expenseCategoryList.getResultList();
		for (ExpenseCategory expenseCategory : expenseCategories) {
			for (int i = 0; i < expenseCategory.getExpenseTypes().size(); i++) {
				columnsSize++; // each type column
			}
			if (expenseCategory.getExpenseTypes().size() > 1) {
				columnsSize++; // category total
			}
		}
		return columnsSize;
	}
}
