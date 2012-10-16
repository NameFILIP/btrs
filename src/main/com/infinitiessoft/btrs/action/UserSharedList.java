package com.infinitiessoft.btrs.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import com.infinitiessoft.btrs.model.UserShared;

@Name("userSharedList")
public class UserSharedList extends EntityQuery<UserShared> {

	private static final long serialVersionUID = 1L;

	@Override
    protected String getPersistenceContextName() {
         return "userEntityManager";
    }
	
	private static final String EJBQL = "select userShared from UserShared userShared";

	private Map<Long, UserShared> allUsersShared = null;
	
	private static final String[] RESTRICTIONS = {
			"lower(userShared.email) like lower(concat(#{userSharedList.userShared.email},'%'))",
			"lower(userShared.firstName) like lower(concat(#{userSharedList.userShared.firstName},'%'))",
			"lower(userShared.lastName) like lower(concat(#{userSharedList.userShared.lastName},'%'))",
			"lower(userShared.password) like lower(concat(#{userSharedList.userShared.password},'%'))",
			"lower(userShared.username) like lower(concat(#{userSharedList.userShared.username},'%'))",};

	private UserShared userShared = new UserShared();

	public UserSharedList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setOrder("createdDate");
	}

	public UserShared getUserShared() {
		return userShared;
	}
	
	public UserShared getUserShared(String username) {
		userShared.setUsername(username);
		return getSingleResult();
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<Long, UserShared> getAllUsersShared() {
		if (allUsersShared == null) {
			allUsersShared = new HashMap<Long, UserShared>();
			for (UserShared userShared : getResultList()) {
				allUsersShared.put(userShared.getId(), userShared);
			}
		}
		return allUsersShared;
	}
	
	public UserShared getUserShared(Long id) {
		return getAllUsersShared().get(id);
	}
	
}
