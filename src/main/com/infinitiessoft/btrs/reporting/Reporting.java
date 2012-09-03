package com.infinitiessoft.btrs.reporting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.seam.log.LogProvider;
import org.jboss.seam.log.Logging;

import com.infinitiessoft.btrs.enums.ExpenseTypeEnum;
import com.infinitiessoft.btrs.enums.PeriodTypeEnum;

public class Reporting {

	private static final LogProvider log = Logging.getLogProvider(Reporting.class);
	
	private PeriodTypeEnum periodType;
	private Map<Period, SubReporting> subReportings = new HashMap<Period, SubReporting>();

	private Map<String, Map<ExpenseTypeEnum, Integer>> totalCategoryType = new HashMap<String, Map<ExpenseTypeEnum, Integer>>();
	private Map<String, Integer> totalCategory = new HashMap<String, Integer>();
	private Integer total = 0;

	
	public Reporting() {
	}
	
	public Reporting(PeriodTypeEnum periodType) {
		this.periodType = periodType;
	}

	public Map<Period, SubReporting> getSubReportings() {
		return subReportings;
	}

	public void setSubReportings(Map<Period, SubReporting> subReportings) {
		this.subReportings = subReportings;
	}
	
	public SubReporting getSubReporting(Period period) {
		return subReportings.get(period);
	}

	public Map<String, Map<ExpenseTypeEnum, Integer>> getTotalCategoryType() {
		return totalCategoryType;
	}

	public void setTotalCategoryType(Map<String, Map<ExpenseTypeEnum, Integer>> totalCategoryType) {
		this.totalCategoryType = totalCategoryType;
	}
	
	public Integer getTotalCategoryType(String categoryCode, ExpenseTypeEnum expenseType) {
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

	
	public PeriodTypeEnum getPeriodType() {
		return periodType;
	}

	public void setPeriodType(PeriodTypeEnum periodType) {
		this.periodType = periodType;
	}

	public void addReportingRow(Period period, ReportingRow reportingRow) {
		SubReporting subReporting = subReportings.get(period);
		if (subReporting == null) {
			subReporting = new SubReporting();
			subReportings.put(period, subReporting);
		}
		subReporting.addReportingRow(reportingRow);
	}

	@Override
	public String toString() {
		return "Reporting [subReportings=" + subReportings
				+ ", totalCategoryType=" + totalCategoryType + ", totalCategory=" + totalCategory
				+ ", total=" + total + "]";
	}
	
	public List<Period> getSubReportingsKeys() {
		List<Period> keysList = new ArrayList<Period>(subReportings.keySet());
		Collections.sort(keysList);
		return keysList;
	}
	
	private void clearTotals() {
		totalCategoryType.clear();
		totalCategory.clear();
		total = 0;
	}
	
	private void addAmountToTotalCategoryType(String categoryCode, ExpenseTypeEnum expenseType, Integer amount) {
		Map<ExpenseTypeEnum, Integer> totalTypeAmount = totalCategoryType.get(categoryCode);
		if (totalTypeAmount == null) {
			totalTypeAmount = new HashMap<ExpenseTypeEnum, Integer>();
			totalCategoryType.put(categoryCode, totalTypeAmount);
		}
		Integer existingAmount = totalTypeAmount.get(expenseType);
		Integer updatedAmount = null;
		if (existingAmount == null) {
			updatedAmount = amount;
		} else {
			updatedAmount = existingAmount + amount;
		}
		totalTypeAmount.put(expenseType, updatedAmount);
	}
	
	private void addAmountToTotalCategory(String categoryCode, Integer amount) {
		Integer existingAmount = totalCategory.get(categoryCode);
		Integer updatedAmount = null;
		if (existingAmount == null) {
			updatedAmount = amount;
		} else {
			updatedAmount = existingAmount + amount;
		}
		totalCategory.put(categoryCode, updatedAmount);
	}
	
	private void addAmountToTotal(Integer amount) {
		total += amount;
	}
	
	
	public void recalculateTotals() {
		clearTotals();
		for (SubReporting subReporting : subReportings.values()) {
			subReporting.recalculateTotals();
			for (String categoryCode : subReporting.getTotalCategoryType().keySet()) {
				for (ExpenseTypeEnum expenseType : subReporting.getTotalTypeAmount(categoryCode).keySet()) {
					Integer amount = subReporting.getTotalCategoryType(categoryCode, expenseType);
					addAmountToTotalCategoryType(categoryCode, expenseType, amount);
				}
				Integer totalCategory = subReporting.getTotalCategory(categoryCode);
				addAmountToTotalCategory(categoryCode, totalCategory);
			}
			Integer totalRow = subReporting.getTotal();
			addAmountToTotal(totalRow);
		}
	}
	
}
