package com.infinitiessoft.btrs.enums;

public enum ReportTypeEnum {

	OPEN("Open"),
	SUBMITTED("Submitted"),
	REJECTED("Rejected"),
	
	ALL("All"),
	APPROVED("Approved");
	
	
	private final String label;

	ReportTypeEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

}
