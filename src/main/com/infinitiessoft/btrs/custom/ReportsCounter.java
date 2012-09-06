package com.infinitiessoft.btrs.custom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.action.ReportList;
import com.infinitiessoft.btrs.enums.ReportTypeEnum;
import com.infinitiessoft.btrs.enums.StatusEnum;
import com.infinitiessoft.btrs.model.Report;

@Name("reportsCounter")
public class ReportsCounter {

	@Logger Log log;
	
	@In
	ReportList reportList;
	
	@Factory("reportsCounts")
	public Map<String, Integer> prepareCounts() {
		long beginTime = System.currentTimeMillis();
		log.debug("Counting reports has been started");
		
		int openCount = 0;
		int submittedCount = 0;
		int rejectedCount = 0;
		int approvedCount = 0;
		int allCount = 0;

		List<Report> usersReports = reportList.getReportsForUser();

		for (Report report : usersReports) {
			if (report.getCurrentStatus() == StatusEnum.SUBMITTED ) {
				submittedCount++;
			} else if (report.getCurrentStatus() == StatusEnum.REJECTED) {
				rejectedCount++;
			} else if (report.getCurrentStatus() == StatusEnum.APPROVED) {
				approvedCount++;
			}
		}
		openCount = submittedCount + rejectedCount;
		allCount = usersReports.size();

		Map<String, Integer> reportsCounts = new HashMap<String, Integer>();
		reportsCounts.put(ReportTypeEnum.OPEN.getLabel(), openCount);
		reportsCounts.put(ReportTypeEnum.SUBMITTED.getLabel(), submittedCount);
		reportsCounts.put(ReportTypeEnum.REJECTED.getLabel(), rejectedCount);
		
		reportsCounts.put(ReportTypeEnum.ALL.getLabel(), allCount);
		reportsCounts.put(ReportTypeEnum.APPROVED.getLabel(), approvedCount);
		
		log.info("Reports Counts are: {0}", reportsCounts);

		long endTime = System.currentTimeMillis();
		log.info("Counting reports took: #0 ms", endTime - beginTime);
		return reportsCounts;
	}
}
