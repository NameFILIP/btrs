package com.infinitiessoft.btrs.enums;

public enum ExpenseTypeEnum {
	
	BUS("type.bus"),
	CAR("type.car"),
	HSR("type.hsr"),
	INVOICE("type.invoice"),
	MRT("type.mrt"),
	OTHER("type.other"),
	TAXI("type.taxi"),
	TRAIN("type.train");
	
	private final String label;

	ExpenseTypeEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
	
}
