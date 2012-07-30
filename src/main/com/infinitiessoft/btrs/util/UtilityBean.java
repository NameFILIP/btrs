package com.infinitiessoft.btrs.util;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("utilityBean")
@AutoCreate
@Scope(ScopeType.APPLICATION)
public class UtilityBean {
	
	public String getNull() {
		return null;
	}

}
