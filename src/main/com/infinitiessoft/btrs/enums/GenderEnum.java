package com.infinitiessoft.btrs.enums;

public enum GenderEnum {
	
	MALE("gender.male"),
	FEMALE("gender.female");

	private final String label;

	GenderEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
}
