package com.infinitiessoft.btrs.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletRequest;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.custom.MailSender;
import com.infinitiessoft.btrs.custom.ReportingDataPreparator;
import com.infinitiessoft.btrs.enums.StatusEnum;
import com.infinitiessoft.btrs.model.Expense;
import com.infinitiessoft.btrs.model.Report;
import com.infinitiessoft.btrs.model.StatusChange;
import com.infinitiessoft.btrs.model.User;
import com.infinitiessoft.btrs.model.UserShared;

@Name("reportHome")
public class ReportHome extends EntityHome<Report> {

	private static final long serialVersionUID = -2118409698027259254L;
	
	@Logger Log log;
	
	@In
	User currentUser;
	
	@In
	ReportingDataPreparator reportingDataPreparator;
	
	@In
	Map<String, String> messages;
	
	@In
	MailSender mailSender;
	
	@In("#{userSharedList.allUsersShared}")
	private Map<Long, UserShared> allUsersShared;
	
	private String actionName;
	private String comment;

	public void setReportId(Long id) {
		setId(id);
	}

	public Long getReportId() {
		return (Long) getId();
	}

	@Override
	protected Report createInstance() {
		Report report = new Report();
		report.setOwner(currentUser);
		return report;
	}
	
	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {}

	public boolean isWired() {
		return true;
	}

	public Report getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Expense> getExpenses() {
		return getInstance() == null ? null : getInstance().getExpenses();
	}
	public List<StatusChange> getStatusChanges() {
		return getInstance() == null ? null : new ArrayList<StatusChange>(
				getInstance().getStatusChanges());
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String persist() {
		Report report = getInstance();
		report.setCreatedDate(new Date());
		report.setMaxIdLastMonth(getMaxIdLastMonth());
		report.setCurrentStatus(StatusEnum.SUBMITTED);
		
		String result = super.persist();
		
		log.info("User #0 created new report (id = #1)", getCurrentUsername(), getReportId());
		
		mailSender.sendSubmittedEmail(prepareMailInfo());
		return result;
	}
	
	private Long getMaxIdLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		cal.add(Calendar.MILLISECOND, -1);
		Date lastMonthEnd = cal.getTime();
		
		Long maxIdLastMonth = (Long) getEntityManager().createQuery("SELECT max(r.id) FROM Report r WHERE r.createdDate <= :lastMonthEnd")
				.setParameter("lastMonthEnd", lastMonthEnd, TemporalType.TIMESTAMP)
				.getSingleResult();
		
		log.debug("MaxIdLastMonth has been selected: #0, before date: #1", maxIdLastMonth, lastMonthEnd);
		return maxIdLastMonth == null ? 0 : maxIdLastMonth ;
	}
	
//	public String generatorPersist() {
//		return super.persist();
//	}

	@Override
	public String update() {
		Report report = getInstance();
		report.setLastUpdatedDate(new Date());
		boolean resubmitted = false;
		if (currentUser.equals(report.getOwner()) && ! currentUser.equals(report.getReviewer())) {
			if (report.getCurrentStatus() == StatusEnum.REJECTED) {
				resubmitted = resubmit();
			} else if (report.getCurrentStatus() == StatusEnum.APPROVED) {
				resubmitted = resubmit();
				reportingDataPreparator.setDirty(true);
			}
		}
		String result = super.update();
		if (resubmitted) {
			mailSender.sendSubmittedEmail(prepareMailInfo());
		}
		return result;
	}
	
	private boolean resubmit() {
		changeStatus(StatusEnum.SUBMITTED, "resubmitted");
		return true;
	}
	
	public String approve() {
		changeStatus(StatusEnum.APPROVED, comment);
		reportingDataPreparator.setDirty(true);
		update();
		log.info("Report (id = #0) has been approved by #1", getReportId(), getCurrentUsername());
		mailSender.sendReviewedEmail(prepareMailInfo());
		return "approved";
	}
	
	public String reject() {
		changeStatus(StatusEnum.REJECTED, comment);
		update();
		log.info("Report (id = #0) has been rejected by #1", getReportId(), getCurrentUsername());
		mailSender.sendReviewedEmail(prepareMailInfo());
		return "rejected";
	}
	
	private void changeStatus(StatusEnum status, String comment) {
		Report report = getInstance();
		
		log.info("Changing status of Report({0}) to {1}, with comment {2}", report, status, comment);
		
		StatusChange statusChange = new StatusChange(currentUser, status, report, comment, new Date());
		report.addStatusChange(statusChange);
		report.setCurrentStatus(status);
	}
	
	private String getRequestURL() {
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
		} else {
			return "";
		}
	}
	
	private Map<String, Object> prepareMailInfo() {
		Map<String, Object> info = new HashMap<String, Object>();
		Report report = getInstance();
		UserShared ownerInfo = allUsersShared.get(report.getOwner().getUserSharedId());
		UserShared reviewerInfo = allUsersShared.get(report.getReviewer().getUserSharedId());
		info.put("report", report);
		info.put("ownerName", ownerInfo.getFullName());
		info.put("ownerAddress", ownerInfo.getEmail());
		info.put("reviewerName", reviewerInfo.getFullName());
		info.put("reviewerAddress", reviewerInfo.getEmail());
		info.put("urlBase", getRequestURL());
		// for localization
		info.put("messages", new HashMap<String, String>(messages));
		return info;
	}
	
	
	public String getCurrentUsername() {
		return allUsersShared.get(currentUser.getUserSharedId()).getUsername();
	}
}
