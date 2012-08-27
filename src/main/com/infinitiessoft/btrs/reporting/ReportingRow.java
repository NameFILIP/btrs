package com.infinitiessoft.btrs.reporting;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.infinitiessoft.btrs.enums.ExpenseTypeEnum;
import com.infinitiessoft.btrs.logic.CustomUtils;
import com.infinitiessoft.btrs.model.User;

public class ReportingRow {
	
	private Date tripEndDate;
	private User employee;
	private Map<String, Map<ExpenseTypeEnum, Integer>> categoryTypeAmount = new HashMap<String, Map<ExpenseTypeEnum, Integer>>();
	
	private Map<String, Integer> totalCategoryEmployee = new HashMap<String, Integer>();
	private Integer totalEmployee = 0;

	
	public ReportingRow() {}
	
	public ReportingRow(Date tripEndDate, User employee) {
		this.tripEndDate = tripEndDate;
		this.employee = employee;
	}
	
	public ReportingRow(ReportingRow original) {
		this.tripEndDate = original.getTripEndDate();
		this.employee = original.getEmployee();
		this.categoryTypeAmount = CustomUtils.deepCopy(original.getCategoryTypeAmount());
		this.totalCategoryEmployee = new HashMap<String, Integer>(original.getTotalCategoryEmployee());
		this.totalEmployee = original.getTotalEmployee();
	}

	public Date getTripEndDate() {
		return tripEndDate;
	}

	public void setTripEndDate(Date tripEndDate) {
		this.tripEndDate = tripEndDate;
	}

	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
	}

	public Map<String, Map<ExpenseTypeEnum, Integer>> getCategoryTypeAmount() {
		return categoryTypeAmount;
	}

	public void setCategoryTypeAmount(Map<String, Map<ExpenseTypeEnum, Integer>> categoryTypeAmount) {
		this.categoryTypeAmount = categoryTypeAmount;
	}
	
	public Map<ExpenseTypeEnum, Integer> getTypeAmount(String categoryCode) {
		return categoryTypeAmount.get(categoryCode);
	}
	
	public Integer getAmount(String categoryCode, ExpenseTypeEnum expenseType) {
		Map<ExpenseTypeEnum, Integer> typeAmount = categoryTypeAmount.get(categoryCode);
		if (typeAmount != null) {
			return typeAmount.get(expenseType);
		} else {
			return null;
		}
	}

	public Map<String, Integer> getTotalCategoryEmployee() {
		return totalCategoryEmployee;
	}

	public void setTotalCategoryEmployee(Map<String, Integer> totalCategoryEmployee) {
		this.totalCategoryEmployee = totalCategoryEmployee;
	}
	
	public Integer getTotalCategory(String categoryCode) {
		return totalCategoryEmployee.get(categoryCode);
	}

	public Integer getTotalEmployee() {
		return totalEmployee;
	}

	public void setTotalEmployee(Integer totalEmployee) {
		this.totalEmployee = totalEmployee;
	}
	
	// put logic (if exists - increment, else put)
	
	public void addCategoryTypeAmount(String categoryCode, ExpenseTypeEnum expenseType, Integer amount) {
		Map<ExpenseTypeEnum, Integer> typeAmountMap = categoryTypeAmount.get(categoryCode);
		if (typeAmountMap == null) {
			typeAmountMap = new HashMap<ExpenseTypeEnum, Integer>();
			categoryTypeAmount.put(categoryCode, typeAmountMap);
		}
		
		Integer amountSum = typeAmountMap.get(expenseType);
		amountSum = amountSum == null ? amount : amountSum + amount;
		typeAmountMap.put(expenseType, amountSum);
	}
	
	public void addTotalCategoryEmployee(String categoryCode, Integer amount) {
		Integer amountSum = totalCategoryEmployee.get(categoryCode);
		amountSum = amountSum == null ? amount : amountSum + amount;
		totalCategoryEmployee.put(categoryCode, amountSum);
	}
	
	public void addTotalEmployee(Integer amount) {
		totalEmployee += amount;
	}

	@Override
	public String toString() {
		return "ReportingRow [tripEndDate=" + tripEndDate + ", employee=" + employee + ", categoryTypeAmount="
				+ categoryTypeAmount + ", totalCategoryEmployee=" + totalCategoryEmployee + ", totalEmployee="
				+ totalEmployee + "]";
	}
	
	
}
