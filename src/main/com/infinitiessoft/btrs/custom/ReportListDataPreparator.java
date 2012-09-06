package com.infinitiessoft.btrs.custom;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.action.ReportList;
import com.infinitiessoft.btrs.enums.ReportTypeEnum;
import com.infinitiessoft.btrs.enums.StatusEnum;
import com.infinitiessoft.btrs.exceptions.BtrsRuntimeException;
import com.infinitiessoft.btrs.logic.CustomUtils;
import com.infinitiessoft.btrs.model.Report;

@Name("reportListDataPreparator")
@Scope(ScopeType.CONVERSATION)
public class ReportListDataPreparator {

	@Logger Log log;
	
	@In
	ReportList reportList;
	
	@Out(required = false)
	List<Report> preparedReports;
	

	private int scrollerPage = 1;


	public int getScrollerPage() {
		return scrollerPage;
	}

	public void setScrollerPage(int scrollerPage) {
		this.scrollerPage = scrollerPage;
	}
	
	

	public void prepareList(String type) {
		long beginTime = System.currentTimeMillis();
		log.debug("Preparing reports has been started");
		
		if (type == null || type.trim().isEmpty()) {
			type = defaultType();
		}
		
		List<Report> reports = null;
		if (ReportTypeEnum.OPEN.name().equalsIgnoreCase(type)) {
			reports = reportList.getOpenStatusReportsForUser();
		} else if (ReportTypeEnum.SUBMITTED.name().equalsIgnoreCase(type)) {
			reports = reportList.getReportsForUserByStatus(StatusEnum.SUBMITTED);
		} else if (ReportTypeEnum.REJECTED.name().equalsIgnoreCase(type)) {
			reports = reportList.getReportsForUserByStatus(StatusEnum.REJECTED);
		} else if (ReportTypeEnum.ALL.name().equalsIgnoreCase(type)) {
			reports = reportList.getReportsWithExpensesForUser();
		} else if (ReportTypeEnum.APPROVED.name().equalsIgnoreCase(type)) {
			reports = reportList.getReportsForUserByStatus(StatusEnum.APPROVED);
		} else {
			throw new BtrsRuntimeException("Wrong type: " + type);
		}
		preparedReports = reports;
		
		long endTime = System.currentTimeMillis();
		log.info("Preparing reports took: #0 ms", endTime - beginTime);
	}

	public String defaultType() {
		String type = ReportTypeEnum.SUBMITTED.name().toLowerCase();
		return CustomUtils.capitalizeFirst(type);
	}
	
}
