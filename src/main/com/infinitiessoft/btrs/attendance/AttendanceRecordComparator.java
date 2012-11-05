package com.infinitiessoft.btrs.attendance;

import java.util.Comparator;

public class AttendanceRecordComparator implements Comparator<AttendanceRecord> {
	
	@Override
	public int compare(AttendanceRecord r1, AttendanceRecord r2) {
		return r1.getStartDate().compareTo(r2.getStartDate());
	}
}
