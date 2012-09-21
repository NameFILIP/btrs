package com.infinitiessoft.btrs.autologin;

import static org.jboss.seam.ScopeType.APPLICATION;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.infinitiessoft.btrs.action.UserList;
import com.infinitiessoft.btrs.model.Role;
import com.infinitiessoft.btrs.model.User;

@Name("org.jboss.seam.security.identityStore")
@Scope(APPLICATION)
public class JpaIdentityStore extends org.jboss.seam.security.management.JpaIdentityStore {

	private static final long serialVersionUID = 2L;

	@In(create = true)
	UserList userList;
	
	@Override
	public boolean isUserEnabled(String username) {
		userList.getUser().setUsername(username);
		User user = null;
		try {
			user = userList.getSingleResult();
		} catch (Exception exeption) {
			return false;
		}
		return user.isEnabled();
	}

	@Override
	public List<String> getImpliedRoles(String username) {
		List<String> result = new ArrayList<String>();
		userList.getUser().setUsername(username);
		User user = null;
		try {
			user = userList.getSingleResult();
		} catch (Exception exeption) {
		}
		
		List<Role> roles =  user.getRoles();
		for (Role role : roles) {
			result.add(role.getValue().name());
		}
		return result;
	}
	
	

}
