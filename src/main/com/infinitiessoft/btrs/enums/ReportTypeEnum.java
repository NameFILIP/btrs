package com.infinitiessoft.btrs.enums;

public enum ReportTypeEnum {

	ALL("all"),
	SUBMITTED("submitted"),
	REJECTED("rejected"),
	
	INCOMING("incoming"),
	OUTGOING("outgoing");
	
	private final String label;

	ReportTypeEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

}
