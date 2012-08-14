package com.infinitiessoft.btrs.enums;

public enum ExpenseTypeEnum {
	
	ACCOMMOD_RECEIPT("type.accommodation.with.receipt"),
	ACCOMMOD_NO_RECEIPT("type.accommodation.no.receipt"),
	
	TRANSP_BUS("type.transportation.bus"),
	TRANSP_CAR("type.transportation.car"),
	TRANSP_HSR_RECEIPT("type.transportation.hsr.with.receipt"),
	TRANSP_HSR_NO_RECEIPT("type.transportation.hsr.no.receipt"),
	TRANSP_MRT("type.transportation.mrt"),
	TRANSP_TAXI("type.transportation.taxi"),
	TRANSP_TRAIN("type.transportation.train"),
	
	MEAL("type.meal"),
	OTHER("type.other");
	
	
	private final String label;

	ExpenseTypeEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
	
}
