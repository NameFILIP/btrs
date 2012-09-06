package com.infinitiessoft.btrs.custom;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.infinitiessoft.btrs.action.ExpenseCategoryList;
import com.infinitiessoft.btrs.model.ExpenseCategory;
import com.infinitiessoft.btrs.model.ExpenseType;

@Name("reportingUtils")
public class ReportingUtils {

	@In(create = true)
	ExpenseCategoryList expenseCategoryList;
	
	@In
	Map<ExpenseCategory, List<ExpenseType>> filteredCategoryTypes;
	
	public Integer tableColumnsSize() {
		int columnsSize = 2; // Employee, Row Total
		
		Set<ExpenseCategory> expenseCategories = filteredCategoryTypes.keySet();
		for (ExpenseCategory expenseCategory : expenseCategories) {
			for (int i = 0; i < filteredCategoryTypes.get(expenseCategory).size(); i++) {
				columnsSize++; // each type column
			}
			if (filteredCategoryTypes.get(expenseCategory).size() > 1) {
				columnsSize++; // category total
			}
		}
		return columnsSize;
	}
	
	
}
