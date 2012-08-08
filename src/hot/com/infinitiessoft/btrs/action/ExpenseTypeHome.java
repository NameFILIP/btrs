package com.infinitiessoft.btrs.action;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.model.Expense;
import com.infinitiessoft.btrs.model.ExpenseCategory;
import com.infinitiessoft.btrs.model.ExpenseType;
import com.infinitiessoft.btrs.model.TypeParameter;

@Name("expenseTypeHome")
public class ExpenseTypeHome extends EntityHome<ExpenseType> {

	@Logger Log log;
	
	private static final long serialVersionUID = -6902673531635448789L;
	
	@In(create = true)
	ExpenseCategoryHome expenseCategoryHome;
	
	

	public void setExpenseTypeId(Long id) {
		setId(id);
	}

	public Long getExpenseTypeId() {
		return (Long) getId();
	}

	@Override
	protected ExpenseType createInstance() {
		ExpenseType expenseType = new ExpenseType();
		return expenseType;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
//		getInstance();
//		ExpenseCategory expenseCategory = expenseCategoryHome.getDefinedInstance();
//		if (expenseCategory != null) {
//			getInstance().setExpenseCategory(expenseCategory);
//		}
	}

	public boolean isWired() {
		return true;
	}

	public ExpenseType getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Expense> getExpenses() {
		return getInstance() == null ? null : new ArrayList<Expense>(
				getInstance().getExpenses());
	}
	
	public List<TypeParameter> getTypeParameters() {
		return getInstance() == null ? null : new ArrayList<TypeParameter>(getInstance().getTypeParameters());
	}
	
	public List<ExpenseCategory> getExpenseCategories() {
		return getInstance() == null ? null : getInstance().getExpenseCategories();
	}

}
