package com.infinitiessoft.btrs.reporting;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.seam.log.LogProvider;
import org.jboss.seam.log.Logging;

import com.infinitiessoft.btrs.enums.ExpenseTypeEnum;
import com.infinitiessoft.btrs.logic.CustomUtils;

public class Reporting {

	private static final LogProvider log = Logging.getLogProvider(Reporting.class);
	
	// private PeriodTypeEnum periodType;
	private Date filterStart;
	private Date filterEnd;
	private Map<Period, SubReporting> subReportings = new HashMap<Period, SubReporting>();

	private Map<String, Map<ExpenseTypeEnum, Integer>> totalCategoryTypeGlobal = new HashMap<String, Map<ExpenseTypeEnum, Integer>>();
	private Map<String, Integer> totalCategoryGlobal = new HashMap<String, Integer>();
	private Integer grandTotal = 0;

	
	public Reporting() {
	}

	public Date getFilterStart() {
		return filterStart;
	}

	public void setFilterStart(Date filterStart) {
		this.filterStart = filterStart;
	}

	public Date getFilterEnd() {
		return filterEnd;
	}

	public void setFilterEnd(Date filterEnd) {
		this.filterEnd = filterEnd;
	}

	public Map<Period, SubReporting> getSubReportings() {
		return subReportings;
	}

	public void setSubReportings(Map<Period, SubReporting> subReportings) {
		this.subReportings = subReportings;
	}

	public Map<String, Map<ExpenseTypeEnum, Integer>> getTotalCategoryTypeGlobal() {
		return totalCategoryTypeGlobal;
	}

	public void setTotalCategoryTypeGlobal(Map<String, Map<ExpenseTypeEnum, Integer>> totalCategoryTypeGlobal) {
		this.totalCategoryTypeGlobal = totalCategoryTypeGlobal;
	}
	
	public Integer getTotalCategoryTypeGlobal(String categoryCode, ExpenseTypeEnum expenseType) {
		Map<ExpenseTypeEnum, Integer> typeAmount = totalCategoryTypeGlobal.get(categoryCode);
		if (typeAmount != null) {
			return typeAmount.get(expenseType);
		} else {
			return null;
		}
	}

	public Map<String, Integer> getTotalCategoryGlobal() {
		return totalCategoryGlobal;
	}

	public void setTotalCategoryGlobal(Map<String, Integer> totalCategoryGlobal) {
		this.totalCategoryGlobal = totalCategoryGlobal;
	}

	public Integer getTotalCategoryGlobal(String categoryCode) {
		return totalCategoryGlobal.get(categoryCode);
	}
	
	public Integer getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Integer grandTotal) {
		this.grandTotal = grandTotal;
	}

	
	public void addReportingRow(Period period, ReportingRow reportingRow) {
		log.debug("Addming ReportingRow: " + reportingRow + " with Period: " + period + " to Reporting");
		SubReporting subReporting = subReportings.get(period);
		if (subReporting == null) {
			subReporting = new SubReporting();
			subReportings.put(period, subReporting);
		}
		
		// before update
		Map<String, Map<ExpenseTypeEnum, Integer>> existingCategoryType = CustomUtils.deepCopy(subReporting.getTotalCategoryType());
		Map<String, Integer> existingCategory = new HashMap<String, Integer>(subReporting.getTotalCategory());
		Integer existingSubGrandTotal = subReporting.getSubGrandTotal();
		
		subReporting.addReportingRow(reportingRow);
		
		// after update
		Map<String, Map<ExpenseTypeEnum, Integer>> updatedCategoryType = subReporting.getTotalCategoryType();
		Map<String, Integer> updatedCategory = subReporting.getTotalCategory();
		Integer updatedSubGrandTotal = subReporting.getSubGrandTotal();
		
		// recalculate totals
		for (String categoryCode : updatedCategoryType.keySet()) {
			
			// totalCategoryType
			Map<ExpenseTypeEnum, Integer> updatedType = updatedCategoryType.get(categoryCode);
			Map<ExpenseTypeEnum, Integer> existingType = existingCategoryType.get(categoryCode);
			Map<ExpenseTypeEnum, Integer> totalType = totalCategoryTypeGlobal.get(categoryCode);
			if (totalType == null) {
				totalType = new HashMap<ExpenseTypeEnum, Integer>();
				totalCategoryTypeGlobal.put(categoryCode, totalType);
			}
			
			for (ExpenseTypeEnum expenseType : updatedType.keySet()) {
				Integer existingTotalTypeAmount = existingType != null ? existingType.get(expenseType) : null;
				Integer updatedTotalTypeAmount = updatedType.get(expenseType);
				
				Integer newTotalTypeAmount = CustomUtils.safeSum(totalType.get(expenseType), CustomUtils.safeDifference(existingTotalTypeAmount, updatedTotalTypeAmount));
				totalType.put(expenseType, newTotalTypeAmount);
			}
			
			// totalCategory
			Integer existingCategoryAmount = existingCategory.get(categoryCode);
			Integer updatedCategoryAmount = updatedCategory.get(categoryCode);
			Integer newTotalCategoryAmount = CustomUtils.safeSum(totalCategoryGlobal.get(categoryCode), CustomUtils.safeDifference(existingCategoryAmount, updatedCategoryAmount));
			totalCategoryGlobal.put(categoryCode, newTotalCategoryAmount);
		}
		
		// grandTotal
		grandTotal += CustomUtils.safeDifference(existingSubGrandTotal, updatedSubGrandTotal);
		
	}

	@Override
	public String toString() {
		return "Reporting [filterStart=" + filterStart + ", filterEnd=" + filterEnd + ", subReportings=" + subReportings
				+ ", totalCategoryTypeGlobal=" + totalCategoryTypeGlobal + ", totalCategoryGlobal=" + totalCategoryGlobal
				+ ", grandTotal=" + grandTotal + "]";
	}
	
	public Map<Period, SubReporting> filteredSubReportings() {
		return subReportings;
	}
	
	public List<Period> filteredSubReportingsKeys() {
		return new ArrayList<Period>(subReportings.keySet());
	}
	
}
