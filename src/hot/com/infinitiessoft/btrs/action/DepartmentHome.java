package com.infinitiessoft.btrs.action;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import com.infinitiessoft.btrs.model.Department;
import com.infinitiessoft.btrs.model.User;

@Name("departmentHome")
public class DepartmentHome extends EntityHome<Department> {

	private static final long serialVersionUID = -1802915569261609422L;

	public void setDepartmentId(Long id) {
		setId(id);
	}

	public Long getDepartmentId() {
		return (Long) getId();
	}

	@Override
	protected Department createInstance() {
		Department department = new Department();
		return department;
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

	public Department getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<User> getUsers() {
		return getInstance() == null ? null : new ArrayList<User>(getInstance()
				.getUsers());
	}
	
	public String remove(Department department) {
		setInstance(department);
		return remove();
	}

}
