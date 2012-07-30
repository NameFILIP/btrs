package com.infinitiessoft.btrs.logic;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.infinitiessoft.btrs.enums.HighSpeedRailEnum;

@Name("tripCalculatorHelper")
@Scope(ScopeType.CONVERSATION)
public class TripCalculatorHelper {
	
	private HighSpeedRailEnum source;
	private HighSpeedRailEnum destination;
	private Integer numberOfTickets;
	
	public HighSpeedRailEnum getSource() {
		return source;
	}
	public void setSource(HighSpeedRailEnum source) {
		this.source = source;
	}
	public HighSpeedRailEnum getDestination() {
		return destination;
	}
	public void setDestination(HighSpeedRailEnum destination) {
		this.destination = destination;
	}
	public Integer getNumberOfTickets() {
		return numberOfTickets;
	}
	public void setNumberOfTickets(Integer numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}
	
	public boolean isReady() {
		return (source != null) && (destination != null) && (numberOfTickets != null);
	}

}
