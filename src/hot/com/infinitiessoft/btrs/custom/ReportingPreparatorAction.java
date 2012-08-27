package com.infinitiessoft.btrs.custom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.action.ExpenseCategoryList;
import com.infinitiessoft.btrs.action.ReportList;
import com.infinitiessoft.btrs.enums.PeriodTypeEnum;
import com.infinitiessoft.btrs.model.Expense;
import com.infinitiessoft.btrs.model.ExpenseCategory;
import com.infinitiessoft.btrs.model.ExpenseType;
import com.infinitiessoft.btrs.model.Report;
import com.infinitiessoft.btrs.reporting.Period;
import com.infinitiessoft.btrs.reporting.Reporting;
import com.infinitiessoft.btrs.reporting.ReportingRow;

@Name("reportingPreparatorAction")
public class ReportingPreparatorAction {

	@Logger Log log;
	
	@In(create = true)
	ReportList reportList;
	
	@In(create = true)
	ExpenseCategoryList expenseCategoryList;
	
	Reporting reportingsByYear = new Reporting();
	Reporting reportingsByQuarter = new Reporting();
	Reporting reportingsByMonth = new Reporting();
	
//	@Factory("reportingRows")
	private List<ReportingRow> constructReportingRows() {
		long beginTime = System.currentTimeMillis();
		List<ReportingRow> reportingRows = new ArrayList<ReportingRow>();
		
		reportList.setMaxResults(null);
		List<Report> allReports = reportList.getResultList();
		
		for (Report report : allReports) {
			ReportingRow reportingRow = new ReportingRow(report.getEndDate(), report.getOwner());
			for (Expense expense : report.getExpenses()) {
				ExpenseType expenseType = expense.getExpenseType();
				Integer amount = expense.getTotalAmount();
				for (ExpenseCategory expenseCategory : expenseType.getExpenseCategories()) {
					reportingRow.addCategoryTypeAmount(expenseCategory.getCode(), expenseType.getValue(), amount);
					reportingRow.addTotalCategoryEmployee(expenseCategory.getCode(), amount);
				}
				reportingRow.addTotalEmployee(amount);
			}
			reportingRows.add(reportingRow);
			log.debug("New ReportingRow object is constructed: #0", reportingRow);
		}
		
		long endTime = System.currentTimeMillis();
		log.debug("Constructing reporting rows took: #0 ms", endTime - beginTime);
		return reportingRows;
	}
	
	public void constructReportings() {
		List<ReportingRow> reportingRows = constructReportingRows();
		
		for (ReportingRow reportingRow : reportingRows) {
			Date date = reportingRow.getTripEndDate();
			Period year = new Period(date, PeriodTypeEnum.YEAR);
			Period quarter = new Period(date, PeriodTypeEnum.QUARTER);
			Period month = new Period(date, PeriodTypeEnum.MONTH);
			
			reportingsByYear.addReportingRow(year, reportingRow);
			reportingsByQuarter.addReportingRow(quarter, reportingRow);
			reportingsByMonth.addReportingRow(month, reportingRow);
			
			log.debug("Periods: year #0, quarter #1, month #2", year, quarter, month);
			log.debug("Reportings are: year #0, quarter #1, month #2", reportingsByYear, reportingsByQuarter, reportingsByMonth);
		}
		
	}
	
	public Integer tableColumnsSize() {
		int columnsSize = 2; // Employee, Row Total
		
		List<ExpenseCategory> expenseCategories = expenseCategoryList.getResultList();
		for (ExpenseCategory expenseCategory : expenseCategories) {
			for (ExpenseType expenseType : expenseCategory.getExpenseTypes()) {
				columnsSize++; // each type column
			}
			if (expenseCategory.getExpenseTypes().size() > 1) {
				columnsSize++; // category total
			}
		}
		return columnsSize;
	}
	
	public Reporting getReportingByType(String periodType) {
		PeriodTypeEnum periodTypeEnum = null;
		try {
			periodTypeEnum = PeriodTypeEnum.valueOf(periodType.toUpperCase());
		} catch (Exception e) {
			periodTypeEnum = PeriodTypeEnum.YEAR;
		}
		
		if (periodTypeEnum == PeriodTypeEnum.MONTH) {
			return reportingsByMonth;
		} else if (periodTypeEnum == PeriodTypeEnum.QUARTER) {
			return reportingsByQuarter;
		} else {
			return reportingsByYear;
		}
	}
}
