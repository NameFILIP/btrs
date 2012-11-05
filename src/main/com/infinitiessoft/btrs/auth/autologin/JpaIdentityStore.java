package com.infinitiessoft.btrs.autologin;

import static org.jboss.seam.ScopeType.APPLICATION;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.infinitiessoft.btrs.action.UserList;
import com.infinitiessoft.btrs.action.UserSharedList;
import com.infinitiessoft.btrs.model.Role;
import com.infinitiessoft.btrs.model.User;
import com.infinitiessoft.btrs.model.UserShared;

@Name("org.jboss.seam.security.identityStore")
@Scope(APPLICATION)
public class JpaIdentityStore extends org.jboss.seam.security.management.JpaIdentityStore {

	private static final long serialVersionUID = 2L;

	@In(create = true)
	UserList userList;
	
	@In
	UserSharedList userSharedList;
	
	@Override
	public boolean isUserEnabled(String username) {
		return userSharedList.getUserShared(username).getEnabled();
	}

	@Override
	public List<String> getImpliedRoles(String username) {
		UserShared userShared = userSharedList.getUserShared(username);
		User user = userList.getUserBySharedId(userShared.getId());

		List<String> result = new ArrayList<String>();
		List<Role> roles =  user.getRoles();
		for (Role role : roles) {
			result.add(role.getValue().name());
		}
		return result;
	}
	
	

}
