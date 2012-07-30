package com.infinitiessoft.btrs.action;

import java.util.Arrays;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import com.infinitiessoft.btrs.model.Report;

@Name("reportList")
public class ReportList extends EntityQuery<Report> {

	private static final long serialVersionUID = 3852316905644428617L;

	private static final String EJBQL = "select report from Report report";

	private static final String[] RESTRICTIONS = {
			"lower(report.comment) like lower(concat(#{reportList.report.comment},'%'))",
			"lower(report.reason) like lower(concat(#{reportList.report.reason},'%'))",
			"lower(report.route) like lower(concat(#{reportList.report.route},'%'))",};

	private Report report = new Report();

	public ReportList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Report getReport() {
		return report;
	}
}
