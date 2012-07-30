package com.infinitiessoft.btrs.logic;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.infinitiessoft.btrs.enums.HighSpeedRailEnum;

@Name("highSpeedRailPrices")
@Scope(ScopeType.STATELESS)
public class HighSpeedRailPrices {

	private static int[][] pricesNonReserved = {
	    // from Taipei
		{ 0, 35, 155, 280, 675, 1045, 1305, 1445 },
	    // from Banqiao
		{ 35, 0, 125, 250, 645, 1015, 1280, 1415 },
	    // from Taoyuan
		{ 155, 125, 0, 125, 520, 890, 1150, 1290 },
	    // from Hsinchu
		{ 280, 250, 125, 0, 395, 765, 1025, 1160 },
	    // from Taichung
		{ 675, 645, 520, 395, 0, 365, 630, 765 },
	    // from Chiayi
		{ 1045, 1015, 890, 765, 365, 0, 270, 395 },
	    // from Tainan
		{ 1305, 1280, 1150, 1025, 630, 270, 0, 135 },
	    // from Zuoying
		{ 1445, 1415, 1290, 1160, 765, 395, 135, 0 }
	};
	
	public int getNonReservedPrice(HighSpeedRailEnum source, HighSpeedRailEnum destination) {
		int from = source.getIndex();
		int to = destination.getIndex();
		return pricesNonReserved[from][to];
	}
	
}
