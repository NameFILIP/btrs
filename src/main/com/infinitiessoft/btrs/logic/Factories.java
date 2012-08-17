package com.infinitiessoft.btrs.logic;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.enums.GenderEnum;
import com.infinitiessoft.btrs.enums.HighSpeedRailEnum;

@Name("factories")
public class Factories {

	@Logger Log log;
	
	@Factory(value = "genders", scope = ScopeType.APPLICATION)
	public GenderEnum[] getGenders() {
		return GenderEnum.values();
	}
	
	@Factory(value = "hsrStations", scope = ScopeType.APPLICATION)
	public HighSpeedRailEnum[] getHsrStations() {
		return HighSpeedRailEnum.values();
	}
	
}
