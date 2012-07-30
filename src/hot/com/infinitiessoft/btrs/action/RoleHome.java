package com.infinitiessoft.btrs.action;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import com.infinitiessoft.btrs.model.Role;
import com.infinitiessoft.btrs.model.User;

@Name("roleHome")
public class RoleHome extends EntityHome<Role> {

	private static final long serialVersionUID = 6191002125692260175L;

	public void setRoleId(Long id) {
		setId(id);
	}

	public Long getRoleId() {
		return (Long) getId();
	}

	@Override
	protected Role createInstance() {
		Role role = new Role();
		return role;
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

	public Role getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<User> getUsers() {
		return getInstance() == null ? null : new ArrayList<User>(getInstance()
				.getUsers());
	}
	
	public String remove(Role role) {
		setInstance(role);
		return remove();
	}

}
