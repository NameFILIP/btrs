package com.infinitiessoft.btrs.enums;

public enum StatusEnum {

	SUBMITTED("status.submitted"),
	APPROVED("status.approved"),
	REJECTED("status.rejected");
	
	private final String label;

	StatusEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
}
