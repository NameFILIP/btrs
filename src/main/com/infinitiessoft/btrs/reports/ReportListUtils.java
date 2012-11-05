package com.infinitiessoft.btrs.reports;

import org.jboss.seam.annotations.Name;

import com.infinitiessoft.btrs.enums.ReportTypeEnum;

@Name("reportListUtils")
public class ReportListUtils {

	private static final String CSS_SUCCESS = "success";
	private static final String CSS_WARNING = "warning";
	private static final String CSS_ERROR = "error";
	private static final String CSS_INFO = "info";
	
	public String getHeaderCss(String type) {
		if (ReportTypeEnum.SUBMITTED.name().equalsIgnoreCase(type)) {
			return CSS_WARNING;
		} else if (ReportTypeEnum.REJECTED.name().equalsIgnoreCase(type)) {
			return CSS_ERROR;
		} else if (ReportTypeEnum.APPROVED.name().equalsIgnoreCase(type)) {
			return CSS_SUCCESS;
		} else {
			return CSS_INFO;
		}
	}
	
}
