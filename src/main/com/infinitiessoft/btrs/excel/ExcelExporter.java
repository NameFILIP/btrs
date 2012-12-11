package com.infinitiessoft.btrs.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.enums.ExcelStylesNames;
import com.infinitiessoft.btrs.enums.ParameterEnum;
import com.infinitiessoft.btrs.model.Expense;
import com.infinitiessoft.btrs.model.ExpenseCategory;
import com.infinitiessoft.btrs.model.ExpenseType;
import com.infinitiessoft.btrs.model.ParameterValue;
import com.infinitiessoft.btrs.model.Report;
import com.infinitiessoft.btrs.model.TypeParameter;
import com.infinitiessoft.btrs.model.UserShared;
import com.infinitiessoft.btrs.reports.ReportListDataPreparator;

@Name("excelExporter")
public class ExcelExporter {

//	private static final String MSG_LIST_OF_REPORTS = "excel.list.of.reports";
	private static final String MSG_PAGE = "excel.page";
	
	private static final String MSG_TITLE = "excel.title";
	private static final String MSG_CREATED_DATE = "excel.created.date";
	private static final String MSG_REASON = "excel.reason";
	private static final String MSG_ROUTE = "excel.route";
	private static final String MSG_TRAVEL_DATE = "excel.travel.date";
	private static final String MSG_START = "excel.start";
	private static final String MSG_END = "excel.end";
	private static final String MSG_FORM_COMMENT = "excel.form.comment";
	private static final String MSG_STATUS = "excel.status";
	private static final String MSG_SUMMARY = "excel.summary";
	private static final String MSG_AMOUNT = "excel.amount";
	private static final String MSG_TAX = "excel.tax";
	private static final String MSG_COMMENT = "excel.comment";
	private static final String MSG_PERSONS = "excel.persons";
	private static final String MSG_DAYS = "excel.days";
	private static final String MSG_TOTAL = "excel.total";
	private static final String MSG_ACCOUNTANT = "excel.accountant";
	private static final String MSG_APPLICANT = "excel.applicant";
	
	
	@Logger Log log;
	
	@In
	ReportListDataPreparator reportListDataPreparator;
	
	@In
	private Map<String, String> messages;

	@In
	ExcelStyles excelStyles;
	
	@In("#{userSharedList.allUsersShared}")
	private Map<Long, UserShared> allUsersShared;
	
//	private CellStyle centerAlignStyle;
//	private CellStyle centerVAlignStyle;
//	private CreationHelper createHelper;
	
	int rowIndex = 0;
	
	
	public String getFileName() {
	   return "reportsData.xlsx";
	}
	
	public String getContentType() {
//		return "application/vnd.ms-excel";
		return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	}
	
	public void download() throws IOException {
	    // Prepare.
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

	    // Initialize response.
	    response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
	    response.setContentType(getContentType()); // Check http://www.w3schools.com/media/media_mimeref.asp for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
	    response.setHeader("Content-disposition", "attachment; filename=\"" + getFileName() + "\""); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.

	    // Write file to response.
	    OutputStream output = response.getOutputStream();
	    
	    List<Report> reports = getReports();
	    log.info("Export to Excel is called for #0 reports", reports.size());
	    Workbook wb = generateWorkbook(reports);
	    wb.write(output);
	    
	    output.close();

	    // Inform JSF to not take the response in hands.
	    facesContext.responseComplete(); // Important! Else JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
	}
	
	private List<Report> getReports() {
		List<Report> reports = reportListDataPreparator.getSelectedRows();
		if (reports.isEmpty()) {
			reports = reportListDataPreparator.getPreparedReports();
		}
		return reports;
	}
	
	
	
	
	
	public Workbook generateWorkbook(List<Report> reports) {
		long beginTime = System.currentTimeMillis();
		Workbook wb = new XSSFWorkbook();
		Map<ExcelStylesNames, CellStyle> styles = excelStyles.getStyles(wb);
		
		int pageNumber = 1;
		Sheet sheet = initSheet(messages.get(MSG_PAGE) + " " + pageNumber++, wb);
		
		int rowsPerSheet = 42; // actually 44, but 2 is for safety
		for (int i = 0; i < reports.size(); i++) {
			Report report = reports.get(i);
			int reportHeight = getReportHeight(report);
			if (reportHeight + rowIndex + 1 > rowsPerSheet) {
				sheet = initSheet(messages.get(MSG_PAGE) + " " + pageNumber++, wb);
				rowIndex = 0;
			} else if (i != 0) {
				rowIndex++;
			}
			generateReport(sheet, report, styles);
		}
//		int reportsPerSheet = 3;
//		for (int i = 0; i < reports.size(); i++) {
//			generateReport(sheet, reports.get(i), styles);
//			if ((i + 1) % reportsPerSheet == 0) {
////				sheet.setRowBreak(rowIndex);
//				sheet = initSheet(messages.get(MSG_PAGE) + " " + pageNumber++, wb);
//
//				rowIndex = 0;
//			} else {
//				rowIndex++;
//			}
//		}
		long endTime = System.currentTimeMillis();
		log.info("Generate workbook took: #0 ms", endTime - beginTime);
		return wb;
	}
	
	private Sheet initSheet(String name, Workbook workbook) {
		Sheet sheet = workbook.createSheet(name);
		setColumnWidths(sheet);
//		sheet.setFitToPage(true);
		double margin = 0.4; // inches
		sheet.setMargin(XSSFSheet.TopMargin, margin);
		sheet.setMargin(XSSFSheet.RightMargin, margin);
		sheet.setMargin(XSSFSheet.BottomMargin, margin);
		sheet.setMargin(XSSFSheet.LeftMargin, margin);
		
		Footer footer = sheet.getFooter();
	    footer.setRight( "Page " + HeaderFooter.page() + " of " + HeaderFooter.numPages() );
		
		return sheet;
	}
	
	private void setColumnWidths(Sheet sheet) {
		sheet.setColumnWidth(0, sheet.getColumnWidth(0) * 5 / 4);
		sheet.setColumnWidth(1, sheet.getColumnWidth(1) * 5 / 4);
		sheet.setColumnWidth(2, sheet.getColumnWidth(2) * 3 / 4);
		sheet.setColumnWidth(3, sheet.getColumnWidth(3) * 3 / 4);
		sheet.setColumnWidth(4, sheet.getColumnWidth(4) * 3 / 4);
		sheet.setColumnWidth(5, sheet.getColumnWidth(5) * 3 / 4);
		sheet.setColumnWidth(6, sheet.getColumnWidth(6) * 5 / 4);
		sheet.setColumnWidth(7, sheet.getColumnWidth(7) * 5 / 4);
		sheet.setColumnWidth(8, sheet.getColumnWidth(8) * 3);
	}
	
	private void generateReport(Sheet sheet, Report report, Map<ExcelStylesNames, CellStyle> styles) {
//		long beginTime = System.currentTimeMillis();
		Workbook workbook = sheet.getWorkbook();
		
		int reportRow = 0;
		int reportHeight = getReportHeight(report);
		int reportWidth = getReportWidth();
		
		
		Cell[][] cells = new Cell[reportHeight][reportWidth];
		for (int i = 0; i < reportHeight; i++) {
			Row row = sheet.createRow(rowIndex + i);
			for (int j = 0; j < getReportWidth(); j++) {
				Cell cell = row.createCell(j);
				cell.setCellStyle(styles.get(ExcelStylesNames.DEFAULT));
				cells[i][j] = cell;
			}
		}
		excelStyles.removeBorder(workbook, cells, 0, reportWidth - 1, 1, 0);
		excelStyles.drawOuterBorder(workbook, cells, 2, reportWidth - 1, reportHeight - 1, 0);
		
		// Header Row
		Cell title = cells[reportRow][0];
		title.setCellValue(messages.get(MSG_TITLE));
		title.setCellStyle(styles.get(ExcelStylesNames.TITLE));
		sheet.addMergedRegion(
				new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow, 0, getReportWidth() - 1));
		reportRow++;
		
		Cell createdDateName = cells[reportRow][0];
		createdDateName.setCellValue(messages.get(MSG_CREATED_DATE));
		createdDateName.setCellStyle(styles.get(ExcelStylesNames.CREATED_DATE_NAME));
		sheet.addMergedRegion(
				new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow, 0, 5));
		Cell createdDateValue = cells[reportRow][6];
		createdDateValue.setCellValue(report.getCreatedDate());
		createdDateValue.setCellStyle(styles.get(ExcelStylesNames.CREATED_DATE_VALUE));
		sheet.addMergedRegion(
				new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow, 6, getReportWidth() - 1));
		reportRow++;
		
		// Applicant Row
		Cell applicantTopName = cells[reportRow][0];
		applicantTopName.setCellValue(messages.get(MSG_APPLICANT));
		applicantTopName.setCellStyle(styles.get(ExcelStylesNames.APPLICANT_TOP_NAME));
		Cell applicantTopValue = cells[reportRow][1];
		String applicantFullName = allUsersShared.get(report.getOwner().getUserSharedId()).getFullName();
		applicantTopValue.setCellValue(applicantFullName);
		applicantTopValue.setCellStyle(styles.get(ExcelStylesNames.APPLICANT_TOP_VALUE));
		sheet.addMergedRegion(
				new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow, 1, getReportWidth() - 1));
		reportRow++;
		
		// Reason Row
		Cell reasonName = cells[reportRow][0];
		reasonName.setCellValue(messages.get(MSG_REASON));
		reasonName.setCellStyle(styles.get(ExcelStylesNames.REASON_NAME));
		Cell reasonValue = cells[reportRow][1];
		reasonValue.setCellValue(report.getReason());
		reasonValue.setCellStyle(styles.get(ExcelStylesNames.REASON_VALUE));
		sheet.addMergedRegion(
				new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow, 1, getReportWidth() - 1));
		reportRow++;
		
		// Route Row
		Cell routeName = cells[reportRow][0];
		routeName.setCellValue(messages.get(MSG_ROUTE));
		routeName.setCellStyle(styles.get(ExcelStylesNames.ROUTE_NAME));
		Cell routeValue = cells[reportRow][1];
		routeValue.setCellValue(report.getRoute());
		routeValue.setCellStyle(styles.get(ExcelStylesNames.ROUTE_VALUE));
		sheet.addMergedRegion(
				new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow, 1, getReportWidth() - 1));
		reportRow++;
		
		// Travel Date Row
		Cell travelDateName = cells[reportRow][0];
		travelDateName.setCellValue(messages.get(MSG_TRAVEL_DATE));
		travelDateName.setCellStyle(styles.get(ExcelStylesNames.TRAVEL_DATE));
		Cell startTravelDateName = cells[reportRow][1];
		
		startTravelDateName.setCellValue(messages.get(MSG_START));
		startTravelDateName.setCellStyle(styles.get(ExcelStylesNames.START_NAME));
		Cell startTravelDateValue = cells[reportRow][2];
		startTravelDateValue.setCellValue(report.getStartDate());
		startTravelDateValue.setCellStyle(styles.get(ExcelStylesNames.START_VALUE));
		sheet.addMergedRegion(
				new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow, 2, 5));
		
		Cell endTravelDateName = cells[reportRow][6];
		endTravelDateName.setCellValue(messages.get(MSG_END));
		endTravelDateName.setCellStyle(styles.get(ExcelStylesNames.END_NAME));
		Cell endTravelDateValue = cells[reportRow][7];
		endTravelDateValue.setCellValue(report.getEndDate());
		endTravelDateValue.setCellStyle(styles.get(ExcelStylesNames.END_VALUE));
		sheet.addMergedRegion(
				new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow, 7, 8));
		reportRow++;
		
		// Form Comment Row
		Cell formCommentName = cells[reportRow][0];
		formCommentName.setCellValue(messages.get(MSG_FORM_COMMENT));
		formCommentName.setCellStyle(styles.get(ExcelStylesNames.FORM_COMMENT_NAME));
		Cell formCommentValue = cells[reportRow][1];
		formCommentValue.setCellValue(report.getComment());
		formCommentValue.setCellStyle(styles.get(ExcelStylesNames.FORM_COMMENT_VALUE));
		sheet.addMergedRegion(
				new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow, 1, getReportWidth() - 1));
		reportRow++;
		
		// Expenses Header Row
		Cell summaryName = cells[reportRow][0];
		summaryName.setCellValue(messages.get(MSG_SUMMARY));
		summaryName.setCellStyle(styles.get(ExcelStylesNames.SUMMARY));
		sheet.addMergedRegion(
				new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow, 0, 5));
		Cell amountName = cells[reportRow][6];
		amountName.setCellValue(messages.get(MSG_AMOUNT));
		amountName.setCellStyle(styles.get(ExcelStylesNames.AMOUNT));
		Cell taxName = cells[reportRow][7];
		taxName.setCellValue(messages.get(MSG_TAX));
		taxName.setCellStyle(styles.get(ExcelStylesNames.TAX));
		Cell commentName = cells[reportRow][8];
		commentName.setCellValue(messages.get(MSG_COMMENT));
		commentName.setCellStyle(styles.get(ExcelStylesNames.COMMENT));
		reportRow++;
		
		int totalAmount = 0;
		int totalTax = 0;
		Map<ExpenseCategory, Map<ExpenseType, List<Expense>>> groupedMap = groupExpenses(report.getExpenses());
		for (ExpenseCategory expenseCategory : groupedMap.keySet()) {
			Map<ExpenseType, List<Expense>> groupedSubMap = groupedMap.get(expenseCategory);
			
			Cell expenseCategoryName = cells[reportRow][0];
			expenseCategoryName.setCellValue(messages.get(expenseCategory.getNameKey()));
			expenseCategoryName.setCellStyle(styles.get(ExcelStylesNames.EXPENSE_CATEGORY));
			
			// merge category with expense type or by height
			int subMapSize = groupedSubMap.size();
			int subMapSubSize = 0;
			boolean mergeWithCategory = false;
			for (List<Expense> expenses : groupedSubMap.values()) {
				subMapSubSize += expenses.size();
			}
			if (subMapSize == 1 && isMessagesEqual(
					expenseCategory.getNameKey(), groupedSubMap.keySet().iterator().next().getNameKey())) {
				mergeWithCategory = true;
//				sheet.addMergedRegion(
//						new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow + subMapSubSize - 1, 0, 1));
			} else if (subMapSubSize > 1) {
				sheet.addMergedRegion(
						new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow + subMapSubSize - 1, 0, 0));
			}

			for (ExpenseType expenseType : groupedSubMap.keySet()) {
				Cell expenseTypeName = cells[reportRow][1];
				expenseTypeName.setCellValue(messages.get(expenseType.getNameKey()));
				expenseTypeName.setCellStyle(styles.get(ExcelStylesNames.EXPENSE_TYPE));
				
				// merge expense type cell
				int listSize = groupedSubMap.get(expenseType).size();
				int startMergeColumn = mergeWithCategory ? 0 : 1;
				if (typeHasParameter(expenseType, ParameterEnum.PERSONS) || typeHasParameter(expenseType, ParameterEnum.DAYS)) {
					if (listSize > 1 || mergeWithCategory) {
						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow + listSize - 1, startMergeColumn, 1));
					}
				} else {
					sheet.addMergedRegion(
							new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow + listSize - 1, startMergeColumn, 5));
				}
				
				for (Expense expense : groupedSubMap.get(expenseType)) {

					// Cell expense
					if (typeHasParameter(expenseType, ParameterEnum.PERSONS) && typeHasParameter(expenseType, ParameterEnum.DAYS)) {
						
						ParameterValue personsParam = expense.getParameterValue(ParameterEnum.PERSONS);
						if (personsParam != null) {
							Cell personsValue = cells[reportRow][2];
							personsValue.setCellValue(personsParam.getValue());
							personsValue.setCellStyle(styles.get(ExcelStylesNames.PERSONS_VALUE));
						}
						Cell personsName = cells[reportRow][3];
						personsName.setCellValue(messages.get(MSG_PERSONS));
						personsName.setCellStyle(styles.get(ExcelStylesNames.PERSONS_NAME));
						
						ParameterValue daysParam = expense.getParameterValue(ParameterEnum.DAYS);
						if (daysParam != null) {
							Cell daysValue = cells[reportRow][4];
							daysValue.setCellValue(daysParam.getValue());
							daysValue.setCellStyle(styles.get(ExcelStylesNames.DAYS_VALUE));
						}
						
						Cell daysName = cells[reportRow][5];
						daysName.setCellValue(messages.get(MSG_DAYS));
						daysName.setCellStyle(styles.get(ExcelStylesNames.DAYS_NAME));
					}
					Cell amountValue = cells[reportRow][6];
					amountValue.setCellValue(expense.getTotalAmount());
					amountValue.setCellStyle(styles.get(ExcelStylesNames.MONEY));
					Cell taxValue = cells[reportRow][7];
					taxValue.setCellValue(expense.getTaxAmount());
					taxValue.setCellStyle(styles.get(ExcelStylesNames.MONEY));
					Cell commentValue = cells[reportRow][8];
					commentValue.setCellValue(expense.getComment());
					commentValue.setCellStyle(styles.get(ExcelStylesNames.COMMENT_VALUE));
					
					totalAmount += expense.getTotalAmount();
					totalTax += expense.getTaxAmount();
					
					reportRow++;
				}
			}
		}
		// Total Row
		Cell totalName = cells[reportRow][0];
		totalName.setCellValue(messages.get(MSG_TOTAL));
		totalName.setCellStyle(styles.get(ExcelStylesNames.TOTAL));
		sheet.addMergedRegion(
				new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow, 0, 5));
		Cell totalAmountValue = cells[reportRow][6];
		totalAmountValue.setCellValue(totalAmount);
		totalAmountValue.setCellStyle(styles.get(ExcelStylesNames.MONEY));
		Cell totalTaxValue = cells[reportRow][7];
		totalTaxValue.setCellValue(totalTax);
		totalTaxValue.setCellStyle(styles.get(ExcelStylesNames.MONEY));
		reportRow++;
		
		// Footer Row
		Row footerRow = sheet.getRow(rowIndex + reportRow);
		footerRow.setHeight((short) (footerRow.getHeight() * 3));
		
		Cell statusName = cells[reportRow][0];
		statusName.setCellValue(messages.get(MSG_STATUS));
		statusName.setCellStyle(styles.get(ExcelStylesNames.STATUS_NAME));
		Cell statusValue = cells[reportRow][1];
		statusValue.setCellValue(messages.get(report.getCurrentStatusNameKey()));
		statusValue.setCellStyle(styles.get(ExcelStylesNames.STATUS_VALUE));
		sheet.addMergedRegion(
				new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow, 1, 2));
		
		Cell accountantName = cells[reportRow][3];
		accountantName.setCellValue(messages.get(MSG_ACCOUNTANT));
		accountantName.setCellStyle(styles.get(ExcelStylesNames.ACCONUTANT_NAME));
		Cell accountantValue = cells[reportRow][4];
		String accountantFullName = allUsersShared.get(report.getReviewer().getUserSharedId()).getFullName();
		accountantValue.setCellValue(accountantFullName);
		accountantValue.setCellStyle(styles.get(ExcelStylesNames.ACCOUNTANT_VALUE));
		sheet.addMergedRegion(
				new CellRangeAddress(rowIndex + reportRow, rowIndex + reportRow, 4, getReportWidth() - 3));
		
		Cell appicantName = cells[reportRow][getReportWidth() - 2];
		appicantName.setCellValue(messages.get(MSG_APPLICANT));
		appicantName.setCellStyle(styles.get(ExcelStylesNames.APPLICANT_NAME));
		Cell applicantValue = cells[reportRow][getReportWidth() - 1];
		applicantValue.setCellValue(applicantFullName);
		applicantValue.setCellStyle(styles.get(ExcelStylesNames.APPLICANT_VALUE));
		reportRow++;
		rowIndex += reportRow;
//		long endTime = System.currentTimeMillis();
//		log.info("Generate report #0 took: #1 ms", report.getReason(), endTime - beginTime);
	}
	
	private boolean typeHasParameter(ExpenseType expenseType, ParameterEnum parameterEnum) {
		for (TypeParameter typeParameter : expenseType.getTypeParameters()) {
			if (typeParameter.getValue() == parameterEnum) {
				return true;
			}
		}
		return false;
	}
	
	private Map<ExpenseCategory, Map<ExpenseType, List<Expense>>> groupExpenses(List<Expense> expenses) {
		Map<ExpenseCategory, Map<ExpenseType, List<Expense>>> groupedMap = new HashMap<ExpenseCategory, Map<ExpenseType, List<Expense>>>();
		for (Expense expense : expenses) {
			ExpenseType expenseType = expense.getExpenseType();
			List<ExpenseCategory> expenseCategories = expenseType.getExpenseCategories();
			for (ExpenseCategory expenseCategory : expenseCategories) {
				addExpenseToMap(groupedMap, expenseCategory, expenseType, expense);
			}
		}
		
		return groupedMap;
	}
	
	private boolean addExpenseToMap(Map<ExpenseCategory, Map<ExpenseType, List<Expense>>> groupedMap,
			ExpenseCategory expenseCategory, ExpenseType expenseType, Expense expense) {
		Map<ExpenseType, List<Expense>> groupedSubMap = null;
		if (groupedMap.containsKey(expenseCategory)) {
			groupedSubMap = groupedMap.get(expenseCategory);
		} else {
			groupedSubMap = new HashMap<ExpenseType, List<Expense>>();
			groupedMap.put(expenseCategory, groupedSubMap);
		}
		return addExpenseToSubMap(groupedSubMap, expenseType, expense);
	}
	
	private boolean addExpenseToSubMap(Map<ExpenseType, List<Expense>> groupedSubMap,
			ExpenseType expenseType, Expense expense) {
		List<Expense> expensesList = null;
		if (groupedSubMap.containsKey(expenseType)) {
			expensesList = groupedSubMap.get(expenseType);
		} else {
			expensesList = new ArrayList<Expense>();
			groupedSubMap.put(expenseType, expensesList);
		}
		return expensesList.add(expense);
	}
	
	private int getReportHeight(Report report) {
		int rowCount = 10;// header, created date, applicant top row, reason, route, travel date, comment summary-amount, total, footer
//		Set<ExpenseTypeEnum> uniqueExpenseRows = new HashSet<ExpenseTypeEnum>();
//		for (Expense expense : report.getExpenses()) {
//			uniqueExpenseRows.add(expense.getExpenseType().getValue());
//		}
		rowCount += report.getExpenses().size();
		return rowCount;
	}
	
	
	private int getReportWidth() {
		return 9;
	}
	
	private boolean isMessagesEqual(String msgKey1, String msgKey2) {
		String message1 = messages.get(msgKey1);
		String message2 = messages.get(msgKey2);
		return message1 != null && message2 != null && message1.equals(message2);
	}
	
	

}
