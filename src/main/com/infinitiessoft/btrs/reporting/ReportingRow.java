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
	private Map<String, Map<ExpenseTypeEnum, Integer>> totalCategoryType = new HashMap<String, Map<ExpenseTypeEnum, Integer>>();
	
	private Map<String, Integer> totalCategory = new HashMap<String, Integer>();
	private Integer total = 0;

	
	public ReportingRow() {}
	
	public ReportingRow(Date tripEndDate, User employee) {
		this.tripEndDate = tripEndDate;
		this.employee = employee;
	}
	
	public ReportingRow(ReportingRow original) {
		this.tripEndDate = original.getTripEndDate();
		this.employee = original.getEmployee();
		this.totalCategoryType = CustomUtils.deepCopy(original.getTotalCategoryType());
		this.totalCategory = new HashMap<String, Integer>(original.getTotalCategory());
		this.total = original.getTotal();
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

	public Map<String, Map<ExpenseTypeEnum, Integer>> getTotalCategoryType() {
		return totalCategoryType;
	}

	public void setTotalCategoryType(Map<String, Map<ExpenseTypeEnum, Integer>> totalCategoryType) {
		this.totalCategoryType = totalCategoryType;
	}
	
	public Map<ExpenseTypeEnum, Integer> getTypeAmount(String categoryCode) {
		return totalCategoryType.get(categoryCode);
	}
	
	public Integer getAmount(String categoryCode, ExpenseTypeEnum expenseType) {
		Map<ExpenseTypeEnum, Integer> typeAmount = totalCategoryType.get(categoryCode);
		if (typeAmount != null) {
			return typeAmount.get(expenseType);
		} else {
			return null;
		}
	}

	public Map<String, Integer> getTotalCategory() {
		return totalCategory;
	}

	public void setTotalCategory(Map<String, Integer> totalCategory) {
		this.totalCategory = totalCategory;
	}
	
	public Integer getTotalCategory(String categoryCode) {
		return totalCategory.get(categoryCode);
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public void addCategoryTypeAmount(String categoryCode, ExpenseTypeEnum expenseType, Integer amount) {
		Map<ExpenseTypeEnum, Integer> typeAmount = totalCategoryType.get(categoryCode);
		if (typeAmount == null) {
			typeAmount = new HashMap<ExpenseTypeEnum, Integer>();
			totalCategoryType.put(categoryCode, typeAmount);
		}
		
		Integer amountSum = typeAmount.get(expenseType);
		amountSum = amountSum == null ? amount : amountSum + amount;
		typeAmount.put(expenseType, amountSum);
	}
	
	public void addTotalCategory(String categoryCode, Integer amount) {
		Integer amountSum = totalCategory.get(categoryCode);
		amountSum = amountSum == null ? amount : amountSum + amount;
		totalCategory.put(categoryCode, amountSum);
	}
	
	public void addTotal(Integer amount) {
		total += amount;
	}

	@Override
	public String toString() {
		return "ReportingRow [tripEndDate=" + tripEndDate + ", employee=" + employee + ", totalCategoryType="
				+ totalCategoryType + ", totalCategory=" + totalCategory + ", total=" + total + "]";
	}
	
	
}
