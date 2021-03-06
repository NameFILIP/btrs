package com.infinitiessoft.btrs.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.enums.HighSpeedRailEnum;
import com.infinitiessoft.btrs.enums.ParameterEnum;
import com.infinitiessoft.btrs.model.ApplicationVariable;
import com.infinitiessoft.btrs.model.Expense;
import com.infinitiessoft.btrs.model.ExpenseType;
import com.infinitiessoft.btrs.model.ParameterValue;
import com.infinitiessoft.btrs.model.Report;
import com.infinitiessoft.btrs.model.TypeParameter;
import com.infinitiessoft.btrs.reports.ExpenseAmountCalculator;

@Name("expenseHome")
public class ExpenseHome extends EntityHome<Expense> {

	@Logger Log log;

	private static final long serialVersionUID = 6782484707981187091L;
	
	@In(create = true)
	ExpenseCategoryHome expenseCategoryHome;
	@In(create = true)
	ExpenseTypeHome expenseTypeHome;
	@In(create = true)
	ReportHome reportHome;

	@Out(required = false, scope = ScopeType.EVENT)
	Integer hsrPrice;

	@In(create = true)
	ExpenseAmountCalculator expenseAmountCalculator;
	
	@In(create = true)
	Map<String, ApplicationVariable> allVariables;
	
	
	public void setExpenseId(Long id) {
		setId(id);
	}

	public Long getExpenseId() {
		return (Long) getId();
	}

	@Override
	protected Expense createInstance() {
		Expense expense = new Expense();
		return expense;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}
	
	public void load(Expense expense) {
		if (expense.getId() == null) {
			setInstance(expense);
		} else {
			setExpenseId(expense.getId());
		}
		expenseTypeHome.setExpenseTypeId(expense.getExpenseType().getId());
	}

	public void wire() {
		Expense expense = getInstance();
		log.info("wiring object {0}", expense);
		ExpenseType expenseType = expenseTypeHome.getDefinedInstance();
		if (expenseType != null) {
			expense.setExpenseType(expenseType);
			if ( ! expense.getParameterValues().isEmpty()) {
				log.warn("List of parameter values of expense {0} was not empty: {1}",
						expense, expense.getParameterValues());
				expense.getParameterValues().clear();
			}
			for (TypeParameter typeParameter : expenseType.getTypeParameters()) {
				expense.getParameterValues().add(new ParameterValue(typeParameter, expense));
			}
			
		}
		Report report = reportHome.getInstance();
		if (report != null) {
			getInstance().setReport(report);
		}
	}
	

	public boolean isWired() {
		return true;
	}

	public Expense getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}
	
	public List<ParameterValue> getParameterValues() {
		return getInstance() == null ? null : new ArrayList<ParameterValue>(getInstance().getParameterValues());
	}
	
	public void addToReport() {
		reportHome.getInstance().addExpense(getInstance());
	}
	
	public void clear() {
		log.debug("clear method is called");
		expenseCategoryHome.clearInstance();
		expenseTypeHome.clearInstance();
		clearInstance();
	}
	
	public void addToReportAndClear() {
		Expense expense = getInstance();
		expenseAmountCalculator.calculateTaxAndAmount(expense);
		if ( ! reportHome.getInstance().getExpenses().contains(expense)) {
			addToReport();
		}
		clear();
	}
	
	
	@Factory(value = "hsrDataReady", scope = ScopeType.EVENT)
	public boolean hsrDataReady() {
		String sourceParam = getInstance().getParameterValue(ParameterEnum.SOURCE).getValue();
		String destinationParam = getInstance().getParameterValue(ParameterEnum.DESTINATION).getValue();
		
		boolean hsrDataReady = (sourceParam != null) && (destinationParam != null);
		
		if (hsrDataReady) {
			HighSpeedRailEnum source = HighSpeedRailEnum.valueOf(sourceParam);
			HighSpeedRailEnum destination = HighSpeedRailEnum.valueOf(destinationParam);
			hsrPrice = Integer.valueOf(allVariables.get(source + "_" + destination).getValue());
		}
		return hsrDataReady;
	}
	
	public Double calculateDistance() {
		Expense expense = getInstance();
		String distance = expense.getParameterValue(ParameterEnum.DISTANCE).getValue();
		String carCoefficient = allVariables.get(ApplicationVariableList.CAR_COEFFICIENT).getValue();
		
		try {
			return Integer.valueOf(distance) * Double.valueOf(carCoefficient);
		} catch (NumberFormatException e) {
			return 0.0;
		}
	}

}
