package com.infinitiessoft.btrs.reporting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.seam.log.LogProvider;
import org.jboss.seam.log.Logging;

import com.infinitiessoft.btrs.enums.ExpenseTypeEnum;
import com.infinitiessoft.btrs.logic.UserComparator;
import com.infinitiessoft.btrs.model.User;

public class SubReporting {

	private static final LogProvider log = Logging.getLogProvider(SubReporting.class);
	private static final UserComparator userComparator = new UserComparator();
	
	private Map<User, ReportingRow> reportingRows = new HashMap<User, ReportingRow>();

	private Map<String, Map<ExpenseTypeEnum, Integer>> totalCategoryType = new HashMap<String, Map<ExpenseTypeEnum, Integer>>();
	private Map<String, Integer> totalCategory = new HashMap<String, Integer>();
	private Integer total = 0;

	
	public Map<User, ReportingRow> getReportingRows() {
		return reportingRows;
	}

	public void setReportingRows(Map<User, ReportingRow> reportingRows) {
		this.reportingRows = reportingRows;
	}
	
	public ReportingRow getReportingRow(User user) {
		return reportingRows.get(user);
	}
	
	public List<User> getReportingRowsKeys() {
		List<User> reportingRowsKeys = new ArrayList<User>(reportingRows.keySet());
		Collections.sort(reportingRowsKeys, userComparator);
		return reportingRowsKeys;
	}

	public Map<String, Map<ExpenseTypeEnum, Integer>> getTotalCategoryType() {
		return totalCategoryType;
	}

	public void setTotalCategoryType(Map<String, Map<ExpenseTypeEnum, Integer>> totalCategoryType) {
		this.totalCategoryType = totalCategoryType;
	}
	
	public Map<ExpenseTypeEnum, Integer> getTotalTypeAmount(String categoryCode) {
		return totalCategoryType.get(categoryCode);
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

	public void addReportingRow(ReportingRow reportingRow) {
		User user = reportingRow.getEmployee();
		ReportingRow existingRow = reportingRows.get(user);
		ReportingRow updatedRow = existingRow == null ? reportingRow : mergeTwoReportingRows(existingRow, reportingRow);
		reportingRows.put(user, updatedRow);
	}
	
	private ReportingRow mergeTwoReportingRows(ReportingRow one, ReportingRow two) {
		
		ReportingRow merged = new ReportingRow(one);
		
		// Merging categoryTypeAmount Map
		for (String categoryCode : two.getTotalCategoryType().keySet()) {
			Map<ExpenseTypeEnum, Integer> typeAmountMerged = merged.getTotalCategoryType().get(categoryCode);
			Map<ExpenseTypeEnum, Integer> typeAmountTwo = two.getTotalCategoryType().get(categoryCode);
			
			Integer totalCategory = null;
			Integer total = null;
			
			if (typeAmountMerged == null) {
				typeAmountMerged = new HashMap<ExpenseTypeEnum, Integer>(typeAmountTwo);
				merged.getTotalCategoryType().put(categoryCode, typeAmountMerged);
				
				// calculate totals
				totalCategory = two.getTotalCategory().get(categoryCode);
				total = merged.getTotal() + totalCategory;
			} else {
				int totalCategoryIncrement = 0;
				for (ExpenseTypeEnum expenseType : typeAmountTwo.keySet()) {
					Integer amountSum = typeAmountMerged.get(expenseType);
					if (amountSum == null) {
						amountSum = typeAmountTwo.get(expenseType);
					} else {
						amountSum += typeAmountTwo.get(expenseType);
					}
					typeAmountMerged.put(expenseType, amountSum);
					
					totalCategoryIncrement += typeAmountTwo.get(expenseType);
				}
				// calculate totals
				totalCategory = merged.getTotalCategory().get(categoryCode) + totalCategoryIncrement;
				total = merged.getTotal() + totalCategoryIncrement;
			}
			
			// set totals
			merged.getTotalCategory().put(categoryCode, totalCategory);
			merged.setTotal(total);
		}
		
		return merged;
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
		for (ReportingRow reportingRow : reportingRows.values()) {
			for (String categoryCode : reportingRow.getTotalCategoryType().keySet()) {
				for (ExpenseTypeEnum expenseType : reportingRow.getTypeAmount(categoryCode).keySet()) {
					Integer amount = reportingRow.getAmount(categoryCode, expenseType);
					addAmountToTotalCategoryType(categoryCode, expenseType, amount);
				}
				Integer totalCategory = reportingRow.getTotalCategory(categoryCode);
				addAmountToTotalCategory(categoryCode, totalCategory);
			}
			Integer total = reportingRow.getTotal();
			addAmountToTotal(total);
		}
	}
	
	
	

}
