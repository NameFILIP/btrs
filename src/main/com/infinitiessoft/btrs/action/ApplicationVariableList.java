package com.infinitiessoft.btrs.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.enums.HighSpeedRailEnum;
import com.infinitiessoft.btrs.model.ApplicationVariable;

@Name("applicationVariableList")
public class ApplicationVariableList extends EntityQuery<ApplicationVariable> {

	private static final long serialVersionUID = 1482246647442982463L;

	@Logger Log log;
	
	private static final String CAR_COEFFICIENT = "carCoefficient";

	private static final String EJBQL = "select applicationVariable from ApplicationVariable applicationVariable";

	private static final String[] RESTRICTIONS = {"applicationVariable.name = #{applicationVariableList.applicationVariable.name}"};

	private ApplicationVariable applicationVariable = new ApplicationVariable();

	public ApplicationVariableList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public ApplicationVariable getApplicationVariable() {
		return applicationVariable;
	}
	
	public ApplicationVariable getApplicationVariable(String name) {
		applicationVariable.setName(name);
		return getSingleResult();
	}
	
//	@Factory(value = "carCoefficient", scope = ScopeType.APPLICATION)
//	public Integer getCarCoefficient() {
//		ApplicationVariable variable = getApplicationVariable("carCoefficient");
//		log.debug("Car coefficient Application Variable is: #0", variable.getValue());
//		return Integer.valueOf(variable.getValue());
//	}
	
//	@Factory(value = "hsrPrices", scope = ScopeType.APPLICATION)
//	public Integer[][] getHsrPrices() {
//		int numberOfStations = HighSpeedRailEnum.values().length;
//		Integer[][] prices = new Integer[numberOfStations][numberOfStations];
//		for (HighSpeedRailEnum source : HighSpeedRailEnum.values()) {
//			for (HighSpeedRailEnum destination : HighSpeedRailEnum.values()) {
//				String variableName = source.name() + "_" + destination.name();
//				ApplicationVariable variable = getApplicationVariable(variableName);
//				prices[source.getIndex()][destination.getIndex()] = Integer.valueOf(variable.getValue());
//				log.debug("Loading HSR price for #0. Price: #1", variableName, variable.getValue());
//			}
//		}
//		return prices;
//	}
	
	@Factory(value = "allVariables", scope = ScopeType.APPLICATION)
	public Map<String, ApplicationVariable> getAllVariables() {
		Map<String, ApplicationVariable> allVariables = new HashMap<String, ApplicationVariable>();
		
		ApplicationVariable carCoefficient = getApplicationVariable(CAR_COEFFICIENT);
		allVariables.put(carCoefficient.getName(), carCoefficient);
		log.debug("Car coefficient Application Variable is: #0", carCoefficient.getValue());
		
		Map<String, ApplicationVariable> hsrPrices = getHsrPrices();
		allVariables.putAll(hsrPrices);
		
		return allVariables;
	}
	
	private Map<String, ApplicationVariable> getHsrPrices() {
		Map<String, ApplicationVariable> hsrPrices = new HashMap<String, ApplicationVariable>();

		for (HighSpeedRailEnum source : HighSpeedRailEnum.values()) {
			for (HighSpeedRailEnum destination : HighSpeedRailEnum.values()) {
				String variableName = source.name() + "_" + destination.name();
				ApplicationVariable variable = getApplicationVariable(variableName);
				
				hsrPrices.put(variable.getName(), variable);
				log.debug("Loading HSR price for #0. Price: #1", variable.getName(), variable.getValue());
			}
		}
		return hsrPrices;
	}
	
	
	
	
}
