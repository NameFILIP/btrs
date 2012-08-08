package com.infinitiessoft.btrs.enums;

public enum ParameterEnum {
	
	AMOUNT("parameter.amount"),
	DAYS("parameter.days"),
	DESTINATION("parameter.destination"),
	DISTANCE("parameter.distance"),
	FREEWAY_TOLL("parameter.freeway.toll"),
	HAS_RECEIPT("parameter.has.receipt"),
	INVOICE("parameter.invoice"),
	OTHER("parameter.other"),
	PERSONS("parameter.persons"),
	ROUTE("parameter.route"),
	SOURCE("parameter.source"),
	TAX("parameter.tax"),
	TICKETS("parameter.tickets");
	
	private final String label;

	ParameterEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
	
}
