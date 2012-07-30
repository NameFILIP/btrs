package com.infinitiessoft.btrs.util;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("toggleControlBean")
@Scope(ScopeType.CONVERSATION)
public class ToggleControlBean implements Serializable {

	private static final long serialVersionUID = -4190086720506188561L;
	
	private boolean state;

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	
}
