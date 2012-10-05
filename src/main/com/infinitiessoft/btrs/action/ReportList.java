package com.infinitiessoft.btrs.action;

import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import com.infinitiessoft.btrs.enums.StatusEnum;
import com.infinitiessoft.btrs.model.Report;
import com.infinitiessoft.btrs.model.User;

@Name("reportList")
@AutoCreate
public class ReportList extends EntityQuery<Report> {
	
	private static final long serialVersionUID = 3852316905644428617L;
	
	@In(required = false)
	User currentUser;
	
	private static final String EJBQL = "select report from Report report";

	public static final String[] RESTRICTIONS = {
			"report.owner = #{reportList.report.owner}",
			"report.reviewer = #{reportList.report.reviewer}",
			"report.currentStatus = #{reportList.report.currentStatus}"};
	

	private Report report = new Report();

	public ReportList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
		setOrder("lastUpdatedDate");
	}

	public Report getReport() {
		return report;
	}
	
	public void setReport(Report report) {
		this.report = report;
	}

	/**
	 * Used in constructing Reporting Rows
	 */
	@SuppressWarnings("unchecked")
	public List<Report> getApprovedReportsJoinExpenses() {
		return getEntityManager().createQuery("select distinct r from Report r " +
				"join fetch r.expenses e join fetch e.expenseType et join fetch r.owner o " +
				"where r.currentStatus = 'APPROVED'")
				.getResultList();
	}
	
	/**
	 * Used in counting reports
	 */
	@SuppressWarnings("unchecked")
	public List<Report> getReportsForUser() {
		return getEntityManager().createQuery("select r from Report r " +
				"join fetch r.owner own join fetch r.reviewer rev " +
				"where own = :user or rev = :user")
				.setParameter("user", currentUser)
				.getResultList();
	}
	
	
	/**
	 * Used for preparing All Reports
	 */
	@SuppressWarnings("unchecked")
	public List<Report> getReportsWithExpensesForUser() {
		return getEntityManager().createQuery("select distinct r from Report r " +
				"join fetch r.owner own join fetch r.reviewer rev left outer join fetch r.expenses e " +
				"where own = :user or rev = :user " +
				"order by r.lastUpdatedDate")
				.setParameter("user", currentUser)
				.getResultList();
	}
	
	/**
	 * Used for preparing Submitted, Rejected and Approved Reports
	 */
	@SuppressWarnings("unchecked")
	public List<Report> getReportsForUserByStatus(StatusEnum status) {
		return getEntityManager().createQuery("select distinct r from Report r " +
				"join fetch r.owner own join fetch r.reviewer rev left outer join fetch r.expenses e " +
				"where (own = :user or rev = :user) and r.currentStatus = :status " +
				"order by r.lastUpdatedDate")
				.setParameter("user", currentUser)
				.setParameter("status", status)
				.getResultList();
	}
	
}
