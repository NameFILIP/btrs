package com.infinitiessoft.btrs.enums;

public enum ExpenseTypeEnum {
	
	HSR("hsr"),
	CAR("car");
	
	private final String label;
	
	ExpenseTypeEnum(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
}
