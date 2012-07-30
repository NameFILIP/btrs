package com.infinitiessoft.btrs.action;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.model.ExpenseCategory;
import com.infinitiessoft.btrs.model.ExpenseType;

@Name("expenseCategoryHome")
@Scope(ScopeType.CONVERSATION)
public class ExpenseCategoryHome extends EntityHome<ExpenseCategory> {

	@Logger Log log;
	
	private static final long serialVersionUID = -3523805395037916094L;

	public void setExpenseCategoryId(Long id) {
		setId(id);
	}

	public Long getExpenseCategoryId() {
		return (Long) getId();
	}

	@Override
	protected ExpenseCategory createInstance() {
		ExpenseCategory expenseCategory = new ExpenseCategory();
		return expenseCategory;
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

	public ExpenseCategory getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<ExpenseType> getExpenseTypes() {
		return getInstance() == null ? null : new ArrayList<ExpenseType>(
				getInstance().getExpenseTypes());
	}
	
	
	public String remove(ExpenseCategory expenseCategory) {
		setInstance(expenseCategory);
		return remove();
	}

}
