package com.infinitiessoft.btrs.logic;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;

import com.infinitiessoft.btrs.enums.GenderEnum;
import com.infinitiessoft.btrs.enums.HighSpeedRailEnum;

@Name("factories")
public class Factories {

	@Factory(value = "genders")
	public GenderEnum[] getGenders() {
		return GenderEnum.values();
	}
	
	@Factory(value = "hsrStations", scope = ScopeType.CONVERSATION)
	public HighSpeedRailEnum[] getHsrStations() {
		return HighSpeedRailEnum.values();
	}
	
}
