package com.infinitiessoft.btrs.enums;

public enum ParameterEnum {
	
	DAYS("parameter.days"),
	DESTINATION("parameter.destination"),
	DISTANCE("parameter.distance"),
	FREEWAY_TOLL("parameter.freeway.toll"),
	PERSONS("parameter.persons"),
	ROUTE("parameter.route"),
	SOURCE("parameter.source"),
	TICKETS("parameter.tickets");
	
	private final String label;

	ParameterEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
	
}
