package com.infinitiessoft.btrs.action;

import java.util.Map;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.model.ApplicationVariable;

@Name("applicationVariableHome")
public class ApplicationVariableHome extends EntityHome<ApplicationVariable> {

	private static final long serialVersionUID = 325827626169530042L;
	
	@Logger Log log;
	
	@In
	Map<String, ApplicationVariable> allVariables;

	public void setApplicationVariableId(Long id) {
		setId(id);
	}

	public Long getApplicationVariableId() {
		return (Long) getId();
	}

	@Override
	protected ApplicationVariable createInstance() {
		ApplicationVariable applicationVariable = new ApplicationVariable();
		return applicationVariable;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public ApplicationVariable getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}
	
	public String update(ApplicationVariable variable) {
		setApplicationVariableId(variable.getId());
		return this.update();
	}
	
	public String updateAllVariables() {
		for (ApplicationVariable variable : allVariables.values()) {
			log.debug("Updating Application Variable: #0", variable);
			update(variable);
		}
		StatusMessages.instance().clearGlobalMessages();
		StatusMessages.instance().add("Successfully udated");
		return "updated";
	}

}
