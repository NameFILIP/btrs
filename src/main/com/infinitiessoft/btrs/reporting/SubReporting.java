package com.infinitiessoft.btrs.reporting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.seam.log.LogProvider;
import org.jboss.seam.log.Logging;

import com.infinitiessoft.btrs.enums.ExpenseTypeEnum;
import com.infinitiessoft.btrs.logic.CustomUtils;
import com.infinitiessoft.btrs.logic.UserComparator;
import com.infinitiessoft.btrs.model.User;

public class SubReporting {

	private static final LogProvider log = Logging.getLogProvider(SubReporting.class);
	private static final UserComparator userComparator = new UserComparator();
	
	private Map<User, ReportingRow> reportingRows = new HashMap<User, ReportingRow>();

	private Map<String, Map<ExpenseTypeEnum, Integer>> totalCategoryType = new HashMap<String, Map<ExpenseTypeEnum, Integer>>();
	private Map<String, Integer> totalCategory = new HashMap<String, Integer>();
	private Integer subGrandTotal = 0;

	
	public Map<User, ReportingRow> getReportingRows() {
		return reportingRows;
	}

	public void setReportingRows(Map<User, ReportingRow> reportingRows) {
		this.reportingRows = reportingRows;
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

	public Integer getSubGrandTotal() {
		return subGrandTotal;
	}

	public void setSubGrandTotal(Integer subGrandTotal) {
		this.subGrandTotal = subGrandTotal;
	}

	public void addReportingRow(ReportingRow reportingRow) {
		log.debug("Adding ReporingRow: " + reportingRow + " to subReporting");
		
		User user = reportingRow.getEmployee();
		ReportingRow existingRow = reportingRows.get(user);
		ReportingRow updatedRow = null;
		if (existingRow == null) {
			log.debug("Adding new RR to subReporting");
			updatedRow = reportingRow;
			for (String categoryCode : updatedRow.getCategoryTypeAmount().keySet()) {
				
				// totalCategoryType
				Map<ExpenseTypeEnum, Integer> totalTypeAmount = totalCategoryType.get(categoryCode);
				if (totalTypeAmount == null) {
					totalTypeAmount = new HashMap<ExpenseTypeEnum, Integer>();
					totalCategoryType.put(categoryCode, totalTypeAmount);
				}
				Map<ExpenseTypeEnum, Integer> rowTypeAmount = updatedRow.getTypeAmount(categoryCode);
				
				for (ExpenseTypeEnum expenseType : rowTypeAmount.keySet()) {
					Integer newAmount = CustomUtils.safeSum(totalTypeAmount.get(expenseType), rowTypeAmount.get(expenseType));
					totalTypeAmount.put(expenseType, newAmount);
				}
				
				// totalCategory
				Integer newTotalCategory = CustomUtils.safeSum(totalCategory.get(categoryCode), updatedRow.getTotalCategory(categoryCode));
				totalCategory.put(categoryCode, newTotalCategory);
			}
			// subGrandTotal
			subGrandTotal += updatedRow.getTotalEmployee();
		} else {
			log.debug("Updating existing RR in subReporting");
			updatedRow = mergeTwoReportingRows(existingRow, reportingRow);
			for (String categoryCode : updatedRow.getCategoryTypeAmount().keySet()) {
				
				// totalCategoryType
				Map<ExpenseTypeEnum, Integer> totalTypeAmount = totalCategoryType.get(categoryCode);
				if (totalTypeAmount == null) {
					totalTypeAmount = new HashMap<ExpenseTypeEnum, Integer>();
					totalCategoryType.put(categoryCode, totalTypeAmount);
				}
				Map<ExpenseTypeEnum, Integer> existingTypeAmount = existingRow.getTypeAmount(categoryCode);
				
				Map<ExpenseTypeEnum, Integer> updatedTypeAmount = updatedRow.getTypeAmount(categoryCode);
				for (ExpenseTypeEnum expenseType : updatedTypeAmount.keySet()) {
					Integer existingAmount = existingTypeAmount != null ? existingTypeAmount.get(expenseType) : null;
					Integer updatedAmount = updatedTypeAmount.get(expenseType);
					
					Integer newTotalAmount = CustomUtils.safeSum(totalTypeAmount.get(expenseType), CustomUtils.safeDifference(updatedAmount, existingAmount));
					totalTypeAmount.put(expenseType, newTotalAmount);
				}
				
				// totalCategory
				Integer existingCategoryAmount = existingRow.getTotalCategory(categoryCode);
				Integer updatedCategoryAmount = updatedRow.getTotalCategory(categoryCode);
				Integer newTotalCategory = CustomUtils.safeSum(totalCategory.get(categoryCode), CustomUtils.safeDifference(existingCategoryAmount, updatedCategoryAmount));
				totalCategory.put(categoryCode, newTotalCategory);
			}
			// subGrandTotal
			subGrandTotal += CustomUtils.safeDifference(updatedRow.getTotalEmployee(), existingRow.getTotalEmployee());
		}
		reportingRows.put(user, updatedRow);
	}
	
	private ReportingRow mergeTwoReportingRows(ReportingRow one, ReportingRow two) {
		log.debug("Merging two RR: " + one + " with " + two);
		
		ReportingRow merged = new ReportingRow(one);
		
		// Merging categoryTypeAmount Map
		for (String categoryCode : two.getCategoryTypeAmount().keySet()) {
			Map<ExpenseTypeEnum, Integer> typeAmountMerged = merged.getCategoryTypeAmount().get(categoryCode);
			Map<ExpenseTypeEnum, Integer> typeAmountTwo = two.getCategoryTypeAmount().get(categoryCode);
			
			Integer totalCategoryEmployee = null;
			Integer totalEmployee = null;
			
			if (typeAmountMerged == null) {
				typeAmountMerged = new HashMap<ExpenseTypeEnum, Integer>(typeAmountTwo);
				merged.getCategoryTypeAmount().put(categoryCode, typeAmountMerged);
				
				// calculate totals
				totalCategoryEmployee = two.getTotalCategoryEmployee().get(categoryCode);
				totalEmployee = merged.getTotalEmployee() + totalCategoryEmployee;
			} else {
				int totalCategoryEmployeeIncrement = 0;
				for (ExpenseTypeEnum expenseType : typeAmountTwo.keySet()) {
					Integer amountSum = typeAmountMerged.get(expenseType);
					if (amountSum == null) {
						amountSum = typeAmountTwo.get(expenseType);
					} else {
						amountSum += typeAmountTwo.get(expenseType);
					}
					typeAmountMerged.put(expenseType, amountSum);
					
					totalCategoryEmployeeIncrement += typeAmountTwo.get(expenseType);
				}
				// calculate totals
				totalCategoryEmployee = merged.getTotalCategoryEmployee().get(categoryCode) + totalCategoryEmployeeIncrement;
				totalEmployee = merged.getTotalEmployee() + totalCategoryEmployeeIncrement;
			}
			
			// set totals
			merged.getTotalCategoryEmployee().put(categoryCode, totalCategoryEmployee);
			merged.setTotalEmployee(totalEmployee);
		}
		
		return merged;
	}
	

}
