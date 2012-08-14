package com.infinitiessoft.btrs.action;

import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import com.infinitiessoft.btrs.enums.RoleEnum;
import com.infinitiessoft.btrs.model.Role;
import com.infinitiessoft.btrs.model.User;

@Name("roleList")
public class RoleList extends EntityQuery<Role> {

	private static final long serialVersionUID = -5103495573431901699L;

	private static final String EJBQL = "select role from Role role";

	private static final String[] RESTRICTIONS = {"role.value = #{roleList.role.value}"};

	private Role role = new Role();

	public RoleList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
		setOrder("value");
	}

	public Role getRole() {
		return role;
	}
	
	public Role getDefaultRole() {
		role.setValue(RoleEnum.EMPLOYEE);
		return getSingleResult();
	}
	
	public List<User> getAccountants() {
		role.setValue(RoleEnum.ACCOUNTANT);
		return getSingleResult().getUsers();
	}
}
