package com.infinitiessoft.btrs.enums;

public enum ExpenseCategoryEnum {
	
	TRANSPORTATION("category.transportation"),
	ACCOMODATION("category.accomodation"),
	MEAL("category.meal"),
	OTHER("category.other");
	
	private final String label;

	ExpenseCategoryEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
	
}
