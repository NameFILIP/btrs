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

	private List<User> allUsers = null;
	
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
		setOrder("createdDate");
	}

	public User getUser() {
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		if (allUsers == null) {
			allUsers = getEntityManager().createQuery("select distinct u from User u join fetch u.roles r join fetch u.department d").getResultList();
		}
		return allUsers;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAccountants() {
		List<User> accountants = getEntityManager()
				.createQuery("select distinct u from User u join u.roles r " +
						"where r.value = '" + RoleEnum.ACCOUNTANT + "' and u.username != 'admin'")
				.getResultList();
		return accountants;
	}
	
}
