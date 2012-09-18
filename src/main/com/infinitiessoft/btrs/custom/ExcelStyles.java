package com.infinitiessoft.btrs.custom;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.infinitiessoft.btrs.enums.ExcelStylesNames;

@Name("excelStyles")
@AutoCreate
public class ExcelStyles {

	@In
	private Map<String, String> messages;

	private static final String MSG_FONT_NAME = "excel.font.name";
	private static final String MSG_DATE_FORMAT = "excel.date.format";
	private static final String MSG_CURRENCY_FORMAT = "excel.currency.format";

	public static final short OUTER_BORDER_TYPE = CellStyle.BORDER_MEDIUM;
	public static final short OUTER_BORDER_COLOR = IndexedColors.BLACK.getIndex();
	
	private Map<ExcelStylesNames, CellStyle> styles = null;

	public Map<ExcelStylesNames, CellStyle> getStyles(Workbook workbook) {
		if (styles == null) {
			styles = initStyles(workbook);
		}
		return styles;
	}

	private Map<ExcelStylesNames, CellStyle> initStyles(Workbook workbook) {

		CreationHelper creationHelper = workbook.getCreationHelper();
		short dateDataFormat = creationHelper.createDataFormat().getFormat(messages.get(MSG_DATE_FORMAT));
		short currencyFormat = creationHelper.createDataFormat().getFormat(messages.get(MSG_CURRENCY_FORMAT));
		styles = new HashMap<ExcelStylesNames, CellStyle>();
		
		CellStyle title = createCellStyle(workbook);
		Font font = createFont(workbook, messages.get(MSG_FONT_NAME), 20);
	    title.setFont(font);
	    title.setAlignment(CellStyle.ALIGN_CENTER);
	    title.setWrapText(false);
		noBorder(title);
		styles.put(ExcelStylesNames.TITLE, title);

		CellStyle createdDateName = createCellStyle(workbook);
		createdDateName.setAlignment(CellStyle.ALIGN_RIGHT);
		noBorder(createdDateName);
//		setBottomBorder(createdDateName, CellStyle.BORDER_THICK, IndexedColors.BLACK.getIndex());
		styles.put(ExcelStylesNames.CREATED_DATE_NAME, createdDateName);

		CellStyle createdDateValue = createCellStyle(workbook);
		createdDateValue.setAlignment(CellStyle.ALIGN_LEFT);
		createdDateValue.setDataFormat(dateDataFormat);
		noBorder(createdDateValue);
//		setBottomBorder(createdDateValue, CellStyle.BORDER_THICK, IndexedColors.BLACK.getIndex());
		styles.put(ExcelStylesNames.CREATED_DATE_VALUE, createdDateValue);

		CellStyle reasonName = createCellStyle(workbook);
//		reasonName.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		setTopBorder(reasonName, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		setLeftBorder(reasonName, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		styles.put(ExcelStylesNames.REASON_NAME, reasonName);

		CellStyle reasonValue = createCellStyle(workbook);
		setTopBorder(reasonValue, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		styles.put(ExcelStylesNames.REASON_VALUE, reasonValue);

		CellStyle routeName = createCellStyle(workbook);
		setLeftBorder(routeName, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
//		routeName.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		styles.put(ExcelStylesNames.ROUTE_NAME, routeName);

		CellStyle routeValue = createCellStyle(workbook);
		styles.put(ExcelStylesNames.ROUTE_VALUE, routeValue);

		CellStyle travelDate = createCellStyle(workbook);
		setLeftBorder(travelDate, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		styles.put(ExcelStylesNames.TRAVEL_DATE, travelDate);

		CellStyle startName = createCellStyle(workbook);
		startName.setAlignment(CellStyle.ALIGN_RIGHT);
		styles.put(ExcelStylesNames.START_NAME, startName);

		CellStyle startValue = createCellStyle(workbook);
		startValue.setAlignment(CellStyle.ALIGN_LEFT);
		startValue.setDataFormat(dateDataFormat);
		styles.put(ExcelStylesNames.START_VALUE, startValue);

		CellStyle endName = createCellStyle(workbook);
		endName.setAlignment(CellStyle.ALIGN_RIGHT);
		styles.put(ExcelStylesNames.END_NAME, endName);

		CellStyle endValue = createCellStyle(workbook);
		endValue.setAlignment(CellStyle.ALIGN_LEFT);
		endValue.setDataFormat(dateDataFormat);
		styles.put(ExcelStylesNames.END_VALUE, endValue);

		CellStyle formCommentName = createCellStyle(workbook);
		setLeftBorder(formCommentName, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
//		formCommentName.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		styles.put(ExcelStylesNames.FORM_COMMENT_NAME, formCommentName);

		CellStyle formCommentValue = createCellStyle(workbook);
		styles.put(ExcelStylesNames.FORM_COMMENT_VALUE, formCommentValue);

		CellStyle summary = createCellStyle(workbook);
		summary.setAlignment(CellStyle.ALIGN_CENTER);
		setLeftBorder(summary, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		styles.put(ExcelStylesNames.SUMMARY, summary);

		CellStyle amount = createCellStyle(workbook);
		amount.setAlignment(CellStyle.ALIGN_CENTER);
		styles.put(ExcelStylesNames.AMOUNT, amount);

		CellStyle tax = createCellStyle(workbook);
		tax.setAlignment(CellStyle.ALIGN_CENTER);
		styles.put(ExcelStylesNames.TAX, tax);

		CellStyle comment = createCellStyle(workbook);
		comment.setAlignment(CellStyle.ALIGN_CENTER);
		setRightBorder(comment, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		styles.put(ExcelStylesNames.COMMENT, comment);

		CellStyle expenseCategory = createCellStyle(workbook);
//		expenseCategory.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		setLeftBorder(expenseCategory, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		styles.put(ExcelStylesNames.EXPENSE_CATEGORY, expenseCategory);

		CellStyle expenseType = createCellStyle(workbook);
//		expenseType.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		styles.put(ExcelStylesNames.EXPENSE_TYPE, expenseType);

		CellStyle money = createCellStyle(workbook);
		money.setDataFormat(currencyFormat);
		styles.put(ExcelStylesNames.MONEY, money);

		CellStyle commentValue = createCellStyle(workbook);
		setRightBorder(commentValue, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		styles.put(ExcelStylesNames.COMMENT_VALUE, commentValue);

		CellStyle personsName = createCellStyle(workbook);
		styles.put(ExcelStylesNames.PERSONS_NAME, personsName);

		CellStyle personsValue = createCellStyle(workbook);
		styles.put(ExcelStylesNames.PERSONS_VALUE, personsValue);

		CellStyle daysName = createCellStyle(workbook);
		styles.put(ExcelStylesNames.DAYS_NAME, daysName);

		CellStyle daysValue = createCellStyle(workbook);
		styles.put(ExcelStylesNames.DAYS_VALUE, daysValue);

		CellStyle total = createCellStyle(workbook);
		total.setAlignment(CellStyle.ALIGN_CENTER);
		setLeftBorder(total, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		styles.put(ExcelStylesNames.TOTAL, total);

		CellStyle statusName = createCellStyle(workbook);
		setRightBorder(statusName, CellStyle.BORDER_NONE, OUTER_BORDER_COLOR);
		setBottomBorder(statusName, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		setLeftBorder(statusName, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		styles.put(ExcelStylesNames.STATUS_NAME, statusName);

		CellStyle statusValue = createCellStyle(workbook);
		setBottomBorder(statusValue, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		setLeftBorder(statusValue, CellStyle.BORDER_NONE, OUTER_BORDER_COLOR);
		styles.put(ExcelStylesNames.STATUS_VALUE, statusValue);

		CellStyle accountantName = createCellStyle(workbook);
		accountantName.setAlignment(CellStyle.ALIGN_RIGHT);
		setRightBorder(accountantName, CellStyle.BORDER_NONE, OUTER_BORDER_COLOR);
		setBottomBorder(accountantName, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		styles.put(ExcelStylesNames.ACCONUTANT_NAME, accountantName);

		CellStyle accountantValue = createCellStyle(workbook);
		setBottomBorder(accountantValue, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		setLeftBorder(accountantValue, CellStyle.BORDER_NONE, OUTER_BORDER_COLOR);
		styles.put(ExcelStylesNames.ACCOUNTANT_VALUE, accountantValue);

		CellStyle applicantName = createCellStyle(workbook);
		applicantName.setAlignment(CellStyle.ALIGN_RIGHT);
		setRightBorder(applicantName, CellStyle.BORDER_NONE, OUTER_BORDER_COLOR);
		setBottomBorder(applicantName, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		styles.put(ExcelStylesNames.APPLICANT_NAME, applicantName);

		CellStyle applicantValue = createCellStyle(workbook);
		setRightBorder(applicantValue, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		setBottomBorder(applicantValue, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		setLeftBorder(applicantValue, CellStyle.BORDER_NONE, OUTER_BORDER_COLOR);
		styles.put(ExcelStylesNames.APPLICANT_VALUE, applicantValue);

		CellStyle defaultStyle = createCellStyle(workbook);
		styles.put(ExcelStylesNames.DEFAULT, defaultStyle);
		return styles;
	}

	private CellStyle createCellStyle(Workbook workbook) {
		CellStyle style = createCellStyleNoBorder(workbook);
		setBorder(style, CellStyle.BORDER_THIN, IndexedColors.BLACK.getIndex());
		return style;
	}
	
	private CellStyle createCellStyleNoBorder(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		
		Font font = createFont(workbook, messages.get(MSG_FONT_NAME), 12);
		style.setFont(font);
		
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setWrapText(true);
		return style;
	}
	
	private Font createFont(Workbook workbook, String name, int size) {
		Font font = workbook.createFont();
		font.setFontName(name);
		font.setFontHeightInPoints((short) size);
		return font;
	}
	
	public void setBorder(CellStyle style, short type, short color) {
		setBottomBorder(style, type, color);
		setLeftBorder(style, type, color);
		setRightBorder(style, type, color);
		setTopBorder(style, type, color);
	}
	
	public void setBottomBorder(CellStyle style, short type, short color) {
		style.setBorderBottom(type);
		style.setBottomBorderColor(color);
	}
	
	public void setLeftBorder(CellStyle style, short type, short color) {
		style.setBorderLeft(type);
		style.setLeftBorderColor(color);
	}
	
	public void setRightBorder(CellStyle style, short type, short color) {
		style.setBorderRight(type);
		style.setRightBorderColor(color);
	}
	
	public void setTopBorder(CellStyle style, short type, short color) {
		style.setBorderTop(type);
		style.setTopBorderColor(color);
	}
	
	public void noBorder(CellStyle style) {
		setBorder(style, CellStyle.BORDER_NONE, IndexedColors.BLACK.getIndex());
	}
	
	public void drawThinkBorder(Workbook workbook, Cell[][] cells, int topRow, int rightColumn, int bottomRow, int leftColumn) {
		assert topRow < cells.length && bottomRow < cells.length && bottomRow > topRow;
		assert leftColumn < cells[0].length && rightColumn < cells[0].length && rightColumn > leftColumn;
		
		CellStyle leftStyle = createCellStyle(workbook);
		setLeftBorder(leftStyle, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		
		CellStyle rightStyle = createCellStyle(workbook);
		setRightBorder(rightStyle, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		
		for (int i = topRow; i < bottomRow; i++) {
			Cell left = cells[i][leftColumn];
			Cell right = cells[i][rightColumn];
			left.setCellStyle(leftStyle);
			right.setCellStyle(rightStyle);
		}
		
		CellStyle topStyle = createCellStyle(workbook);
		setTopBorder(topStyle, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		
		CellStyle bottomStyle = createCellStyle(workbook);
		setBottomBorder(bottomStyle, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		
		for (int i = leftColumn; i < rightColumn; i++) {
			Cell top = cells[topRow][i];
			Cell bottom = cells[bottomRow][i];
			top.setCellStyle(topStyle);
			bottom.setCellStyle(bottomStyle);
		}
		// because of creating the border of merged area in POI is sucks
		CellStyle topRightCorner = createCellStyle(workbook);
		setTopBorder(topRightCorner, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		setRightBorder(topRightCorner, OUTER_BORDER_TYPE, OUTER_BORDER_COLOR);
		cells[topRow][rightColumn].setCellStyle(topRightCorner);
	}
	
	public void removeBorder(Workbook workbook, Cell[][] cells, int topRow, int rightColumn, int bottomRow, int leftColumn) {
		assert topRow < cells.length && bottomRow < cells.length && bottomRow > topRow;
		assert leftColumn < cells[0].length && rightColumn < cells[0].length && rightColumn > leftColumn;
		
		CellStyle noBorderStyle = createCellStyle(workbook);
		noBorder(noBorderStyle);
		
		for (int i = topRow; i <= bottomRow; i++) {
			for (int j = leftColumn; j <= rightColumn; j++) {
				cells[i][j].setCellStyle(noBorderStyle);
			}
		}
	}
}
