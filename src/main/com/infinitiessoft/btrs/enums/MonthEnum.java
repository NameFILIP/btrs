package com.infinitiessoft.btrs.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MonthEnum {

	JANUARY("JAN", 0),
	FEBRUARY("FEB", 1),
	MARCH("MAR", 2),
	APRIL("APR", 3),
	MAY("MAY", 4),
	JUNE("JUN", 5),
	JULY("JUL", 6),
	AUGUST("AUG", 7),
	SEPTENBER("SEP", 8),
	OCTOBER("OCT", 9),
	NOVEMBER("NOV", 10),
	DECEMBER("DEC", 11);
	
	private String shortName;
	private Integer number;
	
	private static final Map<Integer, String> mapping = new HashMap<Integer, String>();
	
	static {
        for(MonthEnum m : EnumSet.allOf(MonthEnum.class)) {
        	mapping.put(m.getNumber(), m.getShortName());
        }
   }
	
	MonthEnum(String shortName, Integer number) {
		this.shortName = shortName;
		this.number = number;
	}

	public String getShortName() {
		return this.shortName;
	}
	
	public Integer getNumber() {
		return this.number;
	}
	
	public static String getShortName(Integer number) {
        return mapping.get(number);
   }
}
