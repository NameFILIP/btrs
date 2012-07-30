package com.infinitiessoft.btrs.action;

import java.util.Arrays;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import com.infinitiessoft.btrs.enums.RoleEnum;
import com.infinitiessoft.btrs.model.Role;

@Name("roleList")
public class RoleList extends EntityQuery<Role> {

	private static final long serialVersionUID = -5103495573431901699L;

	private static final String EJBQL = "select role from Role role";

	private static final String[] RESTRICTIONS = {
			"lower(role.comment) like lower(concat(#{roleList.role.comment},'%'))",
			"lower(role.name) like lower(concat(#{roleList.role.name},'%'))",};

	private Role role = new Role();

	public RoleList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Role getRole() {
		return role;
	}
	
	public Role getDefaultRole() {
		return (Role) getEntityManager().createQuery("select r from Role r where r.name = :role")
	            .setParameter("role", RoleEnum.Employee.toString())
	            .getSingleResult();
	}
}
