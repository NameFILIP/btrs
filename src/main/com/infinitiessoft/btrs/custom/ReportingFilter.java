package com.infinitiessoft.btrs.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.action.ExpenseCategoryList;
import com.infinitiessoft.btrs.enums.PeriodTypeEnum;
import com.infinitiessoft.btrs.model.ExpenseCategory;
import com.infinitiessoft.btrs.model.ExpenseType;
import com.infinitiessoft.btrs.model.User;
import com.infinitiessoft.btrs.reporting.Period;
import com.infinitiessoft.btrs.reporting.Reporting;
import com.infinitiessoft.btrs.reporting.ReportingRow;
import com.infinitiessoft.btrs.reporting.SubReporting;

@Name("reportingFilter")
@Scope(ScopeType.CONVERSATION)
public class ReportingFilter {

	@Logger Log log;
	
	private Period startPeriod;
	private Period endPeriod;
	
	private List<User> users = new ArrayList<User>();
	private List<ExpenseType> expenseTypes = new ArrayList<ExpenseType>();
	 
	@In(create = true)
	ExpenseCategoryList expenseCategoryList;
	
	@Out(required = false, scope = ScopeType.EVENT)
	Map<ExpenseCategory, List<ExpenseType>> filteredCategoryTypes;
	

	@Out(required = false, scope = ScopeType.EVENT)
	List<ExpenseCategory> filteredCategoryTypesKeys;
	
	private boolean useFilter = false;
	
	@In(required = false)
	@Out(required = false)
	Reporting currentReporting;
	
	@In(required = false)
	ReportingDataPreparator reportingDataPreparator;
	

	public Period getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(Period startPeriod) {
		this.startPeriod = startPeriod;
	}

	public Period getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(Period endPeriod) {
		this.endPeriod = endPeriod;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<ExpenseType> getExpenseTypes() {
		return expenseTypes;
	}

	public void setExpenseTypes(List<ExpenseType> expenseTypes) {
		this.expenseTypes = expenseTypes;
	}

	public boolean isUseFilter() {
		return useFilter;
	}
	
	public String getUseFilterFixed() {
		return String.valueOf(useFilter);
	}

	public void setUseFilter(boolean useFilter) {
		this.useFilter = useFilter;
	}
	
	public Map<ExpenseCategory, List<ExpenseType>> getFilteredCategoryTypes() {
		return filteredCategoryTypes;
	}

	public void setFilteredCategoryTypes(Map<ExpenseCategory, List<ExpenseType>> filteredCategoryTypes) {
		this.filteredCategoryTypes = filteredCategoryTypes;
	}
	
	
	@End
	public void clear() {
		log.info("ReportingFilter is cleared");
		startPeriod = null;
		endPeriod = null;
		users.clear();
		expenseTypes.clear();
		useFilter = false;
	}
	
	public void filter() {
		log.info("Reporting Filter is used");
		log.debug("filterPeriodStart: #0, filterPeriodEnd: #1", startPeriod, endPeriod);
		log.debug("Filter by users: #0", users);
		
		PeriodTypeEnum periodType = currentReporting.getPeriodType();
		Reporting completeReporting = reportingDataPreparator.getReportingByType(periodType);
		Reporting filteredReporting = new Reporting(periodType);
		
		boolean periodFilter = startPeriod != null || endPeriod != null;
		boolean usersFilter = ! users.isEmpty();

		for (Period period : completeReporting.getSubReportings().keySet()) {
			SubReporting subReporting = completeReporting.getSubReporting(period);
			
			
//			filteredReporting = filterByPeriod(filteredReporting, subReporting, period);
			
			// Period filter
			if (periodFilter) {
				boolean startFilter = startPeriod != null && endPeriod == null && period.compareTo(startPeriod) >= 0;
				boolean endFilter = startPeriod == null && endPeriod != null && period.compareTo(endPeriod) <= 0;
				boolean bothFilters = startPeriod != null && endPeriod != null
						&& period.compareTo(startPeriod) >= 0 && period.compareTo(endPeriod) <= 0;

				if (startFilter || endFilter || bothFilters) {
					filteredReporting.getSubReportings().put(period, subReporting);
				}
			} else {
				filteredReporting.getSubReportings().put(period, subReporting);
			}
			
			// Users filter
			boolean satisfyPeriod = filteredReporting.getSubReporting(period) != null;
			if (usersFilter && satisfyPeriod) {
				
				SubReporting filteredSubReporting = new SubReporting();
				for (User user : users) {
					
					ReportingRow reportingRow = filteredReporting.getSubReporting(period).getReportingRow(user);
					if (reportingRow != null) {
						filteredSubReporting.addReportingRow(reportingRow);
					}
				}
				filteredReporting.getSubReportings().put(period, filteredSubReporting);
			}
				
		}
		filteredReporting.recalculateTotals();
		
		// Remove empty SubReportings
		Set<Period> keysDuplicate = new HashSet<Period>(filteredReporting.getSubReportings().keySet());
		for (Period period : keysDuplicate) {
			if (filteredReporting.getSubReporting(period).getTotal() == 0) {
				filteredReporting.getSubReportings().remove(period);
			}
		}
		
		currentReporting = filteredReporting;
	}
	
//	private Reporting filterByPeriod(Reporting filteredReporting, SubReporting subReporting, Period period) {
//		boolean periodFilter = startPeriod != null || endPeriod != null;
//
//		if (periodFilter) {
//			boolean startFilter = startPeriod != null && endPeriod == null && period.compareTo(startPeriod) >= 0;
//			boolean endFilter = startPeriod == null && endPeriod != null && period.compareTo(endPeriod) <= 0;
//			boolean bothFilters = startPeriod != null && endPeriod != null && period.compareTo(startPeriod) >= 0
//					&& period.compareTo(endPeriod) <= 0;
//
//			if (startFilter || endFilter || bothFilters) {
//				filteredReporting.getSubReportings().put(period, subReporting);
//			}
//		} else {
//			filteredReporting.getSubReportings().put(period, subReporting);
//		}
//		return filteredReporting;
//	}
	
	public boolean filterIsNotEmpty() {
		return ! (startPeriod == null && endPeriod == null && users.isEmpty() && expenseTypes.isEmpty());
	}
	
	public List<Period> getAvailablePeriods(String type) {
		PeriodTypeEnum periodType = currentReporting != null
				? currentReporting.getPeriodType()
				: reportingDataPreparator.getPeriodType(type);
		
		Reporting completeReporting = reportingDataPreparator.getReportingByType(periodType);
		return completeReporting.getSubReportingsKeys();
	}
	
	public void turnOn() {
		log.info("Filter turned on");
		useFilter = true;
	}
	
	public String getReportingType() {
		return currentReporting.getPeriodType().toString().toLowerCase();
	}
	
	public void filterColumns() {
		filteredCategoryTypes = new HashMap<ExpenseCategory, List<ExpenseType>>();
		List<ExpenseCategory> expenseCategories = expenseCategoryList.getResultList();
		for (ExpenseCategory expenseCategory : expenseCategories) {
			if (expenseTypes.isEmpty()) {
				filteredCategoryTypes.put(expenseCategory, expenseCategory.getExpenseTypes());
			} else {
				List<ExpenseType> fullList = new ArrayList<ExpenseType>(expenseCategory.getExpenseTypes());
				
				Iterator<ExpenseType> iter = fullList.iterator();
				while (iter.hasNext()) {
					ExpenseType expenseType = iter.next();
					if ( ! expenseTypes.contains(expenseType)) {
						iter.remove();
					}
				}
				if ( ! fullList.isEmpty()) {
					filteredCategoryTypes.put(expenseCategory, fullList);
				}
			}
		}
		filteredCategoryTypesKeys = new ArrayList<ExpenseCategory>(filteredCategoryTypes.keySet());
		
		Collections.sort(filteredCategoryTypesKeys, Collections.reverseOrder(new Comparator<ExpenseCategory>(){
		       @Override
			public int compare(ExpenseCategory a, ExpenseCategory b){
		           return ((Integer)a.getExpenseTypes().size()).compareTo(b.getExpenseTypes().size());
		        }}));
	}
	
}
