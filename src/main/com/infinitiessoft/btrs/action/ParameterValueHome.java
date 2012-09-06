package com.infinitiessoft.btrs.action;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import com.infinitiessoft.btrs.model.Expense;
import com.infinitiessoft.btrs.model.ParameterValue;
import com.infinitiessoft.btrs.model.TypeParameter;

@Name("parameterValueHome")
public class ParameterValueHome extends EntityHome<ParameterValue> {

	private static final long serialVersionUID = -5977346723046062911L;
	
	@In(create = true)
	TypeParameterHome typeParameterHome;
	@In(create = true)
	ExpenseHome expenseHome;

	public void setParameterValueId(Long id) {
		setId(id);
	}

	public Long getParameterValueId() {
		return (Long) getId();
	}

	@Override
	protected ParameterValue createInstance() {
		ParameterValue parameterValue = new ParameterValue();
		return parameterValue;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		TypeParameter typeParameter = typeParameterHome.getDefinedInstance();
		if (typeParameter != null) {
			getInstance().setTypeParameter(typeParameter);
		}
		Expense expense = expenseHome.getDefinedInstance();
		if (expense != null) {
			getInstance().setExpense(expense);
		}
	}

	public boolean isWired() {
		return true;
	}

	public ParameterValue getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
