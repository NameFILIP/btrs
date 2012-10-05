package com.infinitiessoft.btrs.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.enums.ExpenseTypeEnum;
import com.infinitiessoft.btrs.model.ExpenseType;

@Name("expenseTypeList")
public class ExpenseTypeList extends EntityQuery<ExpenseType> {

	@Logger Log log;
	
	@In(required = false)
	ExpenseTypeHome expenseTypeHome;
	
	private static final long serialVersionUID = -1508828451706319531L;
	
	private List<ExpenseType> allExpenseTypes = null;

	private static final String EJBQL = "select expenseType from ExpenseType expenseType";

	private static final String[] RESTRICTIONS = {};

	private ExpenseType expenseType = new ExpenseType();
	
	public ExpenseTypeList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
		setOrder("value");
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}
	
	public List<ExpenseType> loadByCategory() {
		String[] restrictions = {"expenseType.expenseCategory.id = #{expenseCategory.id}"};
		setRestrictionExpressionStrings(Arrays.asList(restrictions));
		return getResultList();
	}
	
	@Factory(value = "expenseTypes")
	public List<ExpenseTypeEnum> getExpenseTypes() {
		List<ExpenseType> usedExpenseTypes = getResultList();
		List<ExpenseTypeEnum> availableExpenseTypes =
				new ArrayList<ExpenseTypeEnum>(Arrays.asList(ExpenseTypeEnum.values()));
		
		for (ExpenseType expenseType : usedExpenseTypes) {
			availableExpenseTypes.remove(expenseType.getValue());
		}
		
		ExpenseType currentExpenseType = expenseTypeHome.getDefinedInstance();
		if (currentExpenseType != null) {
			availableExpenseTypes.add(currentExpenseType.getValue());
		}
		return availableExpenseTypes;
	}
	
	@SuppressWarnings("unchecked")
	public List<ExpenseType> getAllExpenseTypes() {
		if (allExpenseTypes == null) {
			allExpenseTypes = getEntityManager().createQuery("select distinct et from ExpenseType et join fetch et.typeParameters tp").getResultList();
		}
		return allExpenseTypes;
	}
}
	
