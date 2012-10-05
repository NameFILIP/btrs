package com.infinitiessoft.btrs.enums;

public enum RoleEnum {

	EMPLOYEE("role.employee"),
	ACCOUNTANT("role.accountant");
	
	private final String label;

	RoleEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

}
