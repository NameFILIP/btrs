package com.infinitiessoft.btrs.action;

import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import com.infinitiessoft.btrs.enums.RoleEnum;
import com.infinitiessoft.btrs.model.User;

@Name("userList")
public class UserList extends EntityQuery<User> {

	private static final long serialVersionUID = -3995996897057131759L;

	private static final String EJBQL = "select user from User user";

	private static final String[] RESTRICTIONS = {
			"lower(user.email) like lower(concat(#{userList.user.email},'%'))",
			"lower(user.firstName) like lower(concat(#{userList.user.firstName},'%'))",
			"lower(user.lastName) like lower(concat(#{userList.user.lastName},'%'))",
			"lower(user.password) like lower(concat(#{userList.user.password},'%'))",
			"lower(user.username) like lower(concat(#{userList.user.username},'%'))",};

	private User user = new User();

	public UserList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public User getUser() {
		return user;
	}
	
	public List<User> getAccountants() {
		String ejbql = "SELECT DISTINCT u FROM User u join u.roles as r "
				+ "WHERE r.value = '" + RoleEnum.ACCOUNTANT + "'";
		setEjbql(ejbql);
		return getResultList();
	}
	
}
