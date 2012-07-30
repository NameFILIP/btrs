package com.infinitiessoft.btrs.enums;

public enum ParameterEnum {
	
	SOURCE("source"),
	DESTINATION("destination"),
	AMOUNT("amount"),
	TAX("tax"),
	TICKETS("tickets");
	
	private final String label;
	
	ParameterEnum(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
}
