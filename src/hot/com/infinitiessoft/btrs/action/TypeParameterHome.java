package com.infinitiessoft.btrs.action;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import com.infinitiessoft.btrs.model.ExpenseType;
import com.infinitiessoft.btrs.model.ParameterValue;
import com.infinitiessoft.btrs.model.TypeParameter;

@Name("typeParameterHome")
public class TypeParameterHome extends EntityHome<TypeParameter> {


	private static final long serialVersionUID = 8850889100113019006L;

	public void setTypeParameterId(Long id) {
		setId(id);
	}

	public Long getTypeParameterId() {
		return (Long) getId();
	}

	@Override
	protected TypeParameter createInstance() {
		TypeParameter typeParameter = new TypeParameter();
		return typeParameter;
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

	public TypeParameter getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<ExpenseType> getExpenseTypes() {
		return getInstance() == null ? null : new ArrayList<ExpenseType>(getInstance().getExpenseTypes());
	}
	
	public List<ParameterValue> getParameterValues() {
		return getInstance() == null ? null : new ArrayList<ParameterValue>(getInstance().getParameterValues());
	}
	
	
	public String remove(TypeParameter typeParameter) {
		setInstance(typeParameter);
		return remove();
	}

}
