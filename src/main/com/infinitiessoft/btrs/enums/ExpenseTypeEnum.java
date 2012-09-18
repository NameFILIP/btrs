package com.infinitiessoft.btrs.enums;

public enum ExpenseTypeEnum {
	
	ACCOMMOD_RECEIPT("type.value.accommodation.with.receipt"),
	ACCOMMOD_NO_RECEIPT("type.value.accommodation.no.receipt"),
	
	TRANSP_BUS("type.value.transportation.bus"),
	TRANSP_CAR("type.value.transportation.car"),
	TRANSP_HSR_RECEIPT("type.value.transportation.hsr.with.receipt"),
	TRANSP_HSR_NO_RECEIPT("type.value.transportation.hsr.no.receipt"),
	TRANSP_MRT("type.value.transportation.mrt"),
	TRANSP_TAXI("type.value.transportation.taxi"),
	TRANSP_TRAIN("type.value.transportation.train"),
	
	MEAL("type.value.meal"),
	OTHER("type.value.other");
	
	
	private final String label;

	ExpenseTypeEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
	
}
