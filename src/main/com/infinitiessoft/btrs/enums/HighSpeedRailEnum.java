package com.infinitiessoft.btrs.enums;

public enum HighSpeedRailEnum {
	TAIPEI ("Taipei", 0),
	BANQIAO ("Banqiao", 1),
	TAOYUAN ("Taoyuan", 2),
	HSINCHU ("Hsinchu", 3),
	TAICHUNG ("Taichung", 4),
	CHIAYI ("Chiayi", 5),
	TAINAN ("Tainan", 6),
	ZUOYING ("Zuoying", 7);
	
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