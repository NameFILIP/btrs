package com.infinitiessoft.btrs.action;

import java.util.Arrays;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import com.infinitiessoft.btrs.model.Department;

@Name("departmentList")
public class DepartmentList extends EntityQuery<Department> {

	private static final long serialVersionUID = 4871627186644541082L;

	private static final String EJBQL = "select department from Department department";

	private static final String[] RESTRICTIONS = {
			"lower(department.comment) like lower(concat(#{departmentList.department.comment},'%'))",
			"lower(department.name) like lower(concat(#{departmentList.department.name},'%'))",};

	private Department department = new Department();

	public DepartmentList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Department getDepartment() {
		return department;
	}
	
}
