package com.infinitiessoft.btrs.custom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.action.ReportList;
import com.infinitiessoft.btrs.enums.PeriodTypeEnum;
import com.infinitiessoft.btrs.model.Expense;
import com.infinitiessoft.btrs.model.ExpenseCategory;
import com.infinitiessoft.btrs.model.ExpenseType;
import com.infinitiessoft.btrs.model.Report;
import com.infinitiessoft.btrs.reporting.Period;
import com.infinitiessoft.btrs.reporting.Reporting;
import com.infinitiessoft.btrs.reporting.ReportingRow;

@Name("reportingDataPreparator")
@Scope(ScopeType.APPLICATION)
@Startup
public class ReportingDataPreparator {

	@Logger Log log;
	
	@In
	ReportList reportList;
	
	Reporting reportingsByYear;
	Reporting reportingsByQuarter;
	Reporting reportingsByMonth;
	
	@In(required = false)
	@Out(required = false, scope = ScopeType.CONVERSATION)
	Reporting currentReporting;
	
	private boolean dirty = true;
	
	
	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	private List<ReportingRow> constructReportingRows() {
		long beginTime = System.currentTimeMillis();
		List<ReportingRow> reportingRows = new ArrayList<ReportingRow>();
		
		List<Report> approvedReports = reportList.getApprovedReportsJoinExpenses();
		log.debug("#0 Approved Reports used for Reporting", approvedReports.size());
		
		for (Report report : approvedReports) {
			ReportingRow reportingRow = new ReportingRow(report.getEndDate(), report.getOwner());
			for (Expense expense : report.getExpenses()) {
				ExpenseType expenseType = expense.getExpenseType();
				Integer amount = expense.getTotalAmount();
				for (ExpenseCategory expenseCategory : expenseType.getExpenseCategories()) {
					reportingRow.addCategoryTypeAmount(expenseCategory.getCode(), expenseType.getValue(), amount);
					reportingRow.addTotalCategory(expenseCategory.getCode(), amount);
				}
				reportingRow.addTotal(amount);
			}
			reportingRows.add(reportingRow);
		}
		
		long endTime = System.currentTimeMillis();
		log.debug("Constructing reporting rows took: #0 ms", endTime - beginTime);
		return reportingRows;
	}
	
	public void constructReportings() {
		long beginTime = System.currentTimeMillis();

		reportingsByYear = new Reporting(PeriodTypeEnum.YEAR);
		reportingsByQuarter = new Reporting(PeriodTypeEnum.QUARTER);
		reportingsByMonth = new Reporting(PeriodTypeEnum.MONTH);
		
		List<ReportingRow> reportingRows = constructReportingRows();

		for (ReportingRow reportingRow : reportingRows) {
			Date date = reportingRow.getTripEndDate();
			Period year = new Period(date, PeriodTypeEnum.YEAR);
			Period quarter = new Period(date, PeriodTypeEnum.QUARTER);
			Period month = new Period(date, PeriodTypeEnum.MONTH);

			reportingsByYear.addReportingRow(year, reportingRow);
			reportingsByQuarter.addReportingRow(quarter, reportingRow);
			reportingsByMonth.addReportingRow(month, reportingRow);

		}
		reportingsByYear.recalculateTotals();
		reportingsByQuarter.recalculateTotals();
		reportingsByMonth.recalculateTotals();

		dirty = false;
		
		long endTime = System.currentTimeMillis();
		log.debug("Constructing Reportings took: #0 ms", endTime - beginTime);
	}
	
	
	public void loadCurrentReporting(String periodType) {
		currentReporting = getReportingByType(periodType);
		log.debug("Reporing for type #0 is accessed", periodType);
	}
	
	public PeriodTypeEnum getPeriodType(String periodType) {
		try {
			return PeriodTypeEnum.valueOf(periodType.toUpperCase());
		} catch (Exception e) {
			return PeriodTypeEnum.YEAR;
		}
	}
	
	public Reporting getReportingByType(PeriodTypeEnum periodTypeEnum) {
		if (periodTypeEnum == PeriodTypeEnum.MONTH) {
			return reportingsByMonth;
		} else if (periodTypeEnum == PeriodTypeEnum.QUARTER) {
			return reportingsByQuarter;
		} else {
			return reportingsByYear;
		}
	}
	
	public Reporting getReportingByType(String periodType) {
		PeriodTypeEnum periodTypeEnum = getPeriodType(periodType);
		return getReportingByType(periodTypeEnum);
	}
	
	
	
}
