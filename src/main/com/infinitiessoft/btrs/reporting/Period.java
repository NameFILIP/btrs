package com.infinitiessoft.btrs.reporting;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import org.jboss.seam.core.SeamResourceBundle;

import com.infinitiessoft.btrs.enums.PeriodTypeEnum;

public class Period implements Comparable<Period>{
	
	private Integer year;
	private Integer quarter;
	private Integer month;
	
	private static final String MSG_YEAR = "period.year";
	private static final String MSG_QUARTER = "period.quarter";
	private static final String MSG_MONTH = "period.month";
	private static final String SPACE = " ";
	private static final String SEPARATOR = ",";
	
	
	public Period(Date date, PeriodTypeEnum type) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		year = calendar.get(Calendar.YEAR);
		if (type == PeriodTypeEnum.QUARTER || type == PeriodTypeEnum.MONTH) {
			quarter = calculateQuarter(calendar.get(Calendar.MONTH));
		}
		if (type == PeriodTypeEnum.MONTH) {
			month = calendar.get(Calendar.MONTH);
		}
	}
	
	public Period() {}
	
	public Period(Integer year, Integer quarter, Integer month) {
		this.year = year;
		this.quarter = quarter;
		this.month = month;
	}
	
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getQuarter() {
		return quarter;
	}

	public void setQuarter(Integer quarter) {
		this.quarter = quarter;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((quarter == null) ? 0 : quarter.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Period other = (Period) obj;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		if (quarter == null) {
			if (other.quarter != null)
				return false;
		} else if (!quarter.equals(other.quarter))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	@Override
	public String toString() {
		ResourceBundle resourceBundle = SeamResourceBundle.getBundle();
		String yearLocalized = resourceBundle.getString(MSG_YEAR);
		String quarterLocalized = resourceBundle.getString(MSG_QUARTER);
		String monthLocalized = resourceBundle.getString(MSG_MONTH);
		
		StringBuilder period = new StringBuilder(yearLocalized).append(SPACE).append(year);
		if (quarter != null) {
			period.append(SEPARATOR).append(SPACE).append(quarterLocalized).append(SPACE).append(quarter);
		}
		if (month != null) {
			period.append(SEPARATOR).append(SPACE).append(monthLocalized).append(SPACE).append(month + 1);
//			period.append(", ").append(getMonthForInt(month));
		}
		return period.toString();
	}
	
	public PeriodTypeEnum getType() {
		if (quarter == null) {
			return PeriodTypeEnum.YEAR;
		} else if (month == null) {
			return PeriodTypeEnum.QUARTER;
		} else {
			return PeriodTypeEnum.MONTH;
		}
	}
	
	private Integer calculateQuarter(int month) {
		if (month < 3) {
			return 1;
		} else if (month < 6) {
			return 2;
		} else if (month < 9) {
			return 3;
		} else {
			return 4;
		}
	}

	@Override
	public int compareTo(Period other) {
		if (other == null) {
			// in case start is empty, end is selected. ValidateEquality needs it.
			return 1;
		}
		int result = this.year.compareTo(other.year);
		if (result == 0 && quarter != null) {
			result = this.quarter.compareTo(other.quarter);
		}
		if (result == 0 && month != null) {
			result = this.month.compareTo(other.month);
		}
		return result;
	}
	
	private String getMonthForInt(int month) {
	    DateFormatSymbols dfs = new DateFormatSymbols();
	    String[] months = dfs.getMonths();
	    if (month >= 0 && month <= 11 ) {
	        return months[month];
	    }
	    return null;
	}
	
}
