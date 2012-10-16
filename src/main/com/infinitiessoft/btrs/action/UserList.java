package com.infinitiessoft.btrs.action;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import com.infinitiessoft.btrs.enums.RoleEnum;
import com.infinitiessoft.btrs.model.User;
import com.infinitiessoft.btrs.model.UserShared;

@Name("userList")
public class UserList extends EntityQuery<User> {

	private static final long serialVersionUID = -3995996897057131759L;

	private static final String EJBQL = "select user from User user";

	private List<User> allUsers = null;
	private Map<Long, UserShared> notUsedSharedUsers = null;
	
	@In("#{userSharedList.allUsersShared}")
	private Map<Long, UserShared> allUsersShared;
	
	private static final String[] RESTRICTIONS = {
			"user.userSharedId = #{userList.user.userSharedId}"};

	private User user = new User();

	public UserList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setOrder("id");
	}

	public User getUser() {
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		if (allUsers == null) {
			allUsers = getEntityManager().createQuery("select distinct u from User u left outer join fetch u.roles r left outer join fetch u.department d").getResultList();
		}
		return allUsers;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAccountants() {
		List<User> accountants = getEntityManager()
				.createQuery("select distinct u from User u join u.roles r " +
						"where r.value = '" + RoleEnum.ACCOUNTANT + "'")
				.getResultList();
		
		// filter out admin user
		Iterator<User> iter = accountants.iterator();
		while (iter.hasNext()) {
			User accountant = iter.next();
			UserShared userShared = allUsersShared.get(accountant.getUserSharedId());
			if (userShared.getUsername().equals("admin")) {
				iter.remove();
				break;
			}
		}
		return accountants;
	}

	public User getUserBySharedId(Long id) {
		user.setUserSharedId(id);
		return getSingleResult();
	}
	
	public Collection<UserShared> getNotUsedSharedUsers() {
		if (notUsedSharedUsers == null) {
			notUsedSharedUsers = new HashMap<Long, UserShared>(allUsersShared);
			List<User> allUsers = getResultList();
			for (User user : allUsers) {
				notUsedSharedUsers.remove(user.getUserSharedId());
			}
		}
		return notUsedSharedUsers.values();
	}
	
}
