package com.infinitiessoft.btrs.action;

import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.framework.EntityQuery;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.enums.StatusEnum;
import com.infinitiessoft.btrs.model.Report;
import com.infinitiessoft.btrs.model.User;

@Name("reportList")
public class ReportList extends EntityQuery<Report> {
	
	private static final long serialVersionUID = 3852316905644428617L;
	
	@Logger Log log;
	
	@In
	User currentUser;
	
	
	private static final String IN_TYPE = "in";
	private static final String OUT_TYPE = "out";

	@Out(required = false)
	private List<Report> preparedReports;
	
	private static final String EJBQL = "select report from Report report";

	private static final String[] RESTRICTIONS = {
			"report.owner = #{reportList.report.owner}",
			"report.reviewer = #{reportList.report.reviewer}",
			"report.currentStatus = #{reportList.report.currentStatus}"};

	private Report report = new Report();

	public ReportList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Report getReport() {
		return report;
	}
	
	
	public List<Report> getPreparedReports() {
		return preparedReports;
	}

	public void setPreparedReports(List<Report> preparedReports) {
		this.preparedReports = preparedReports;
	}

	public void prepareList(String type) {
		if (IN_TYPE.equalsIgnoreCase(type)) {
			prepareIncoming();
		} else if (OUT_TYPE.equalsIgnoreCase(type)) {
			prepareOutgoing();
		} else {
			prepareAll();
		}
	}

	@Restrict("#{s:hasRole('ACCOUNTANT')}")
	private void prepareIncoming() {
		log.info("Prepare incoming");
		
		report.setReviewer(currentUser);
		report.setCurrentStatus(StatusEnum.SUBMITTED);
		
		preparedReports = getResultList();
	}

	private void prepareOutgoing() {
		log.info("Prepare outgoing");
		
		report.setOwner(currentUser);
		
		String ejbql = EJBQL + " WHERE report.owner = #{reportList.report.owner}"
				+ " AND report.currentStatus <> '" + StatusEnum.APPROVED + "'";
		setEjbql(ejbql);
		preparedReports = getResultList();
	}

	private void prepareAll() {
		log.info("Prepare all");
		
		report.setOwner(currentUser);
		report.setReviewer(currentUser);
		String ejbql = EJBQL + " WHERE report.reviewer = #{reportList.report.reviewer}"
				+ " OR report.owner = #{reportList.report.owner}";
		setEjbql(ejbql);
		preparedReports = getResultList();
	}
}
