package com.infinitiessoft.btrs.enums;

public enum HighSpeedRailEnum {
	
	TAIPEI ("hsr.station.taipei", 0),
	BANQIAO ("hsr.station.banqiao", 1),
	TAOYUAN ("hsr.station.taoyuan", 2),
	HSINCHU ("hsr.station.hsinchu", 3),
	TAICHUNG ("hsr.station.taichung", 4),
	CHIAYI ("hsr.station.chiayi", 5),
	TAINAN ("hsr.station.tainan", 6),
	ZUOYING ("hsr.station.zuoying", 7);
	
	private final String label;
	private int index;
	
	
	HighSpeedRailEnum(String label, int index) {
		this.label = label;
		this.index = index;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	
}