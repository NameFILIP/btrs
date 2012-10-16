package com.infinitiessoft.btrs.custom;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import com.infinitiessoft.btrs.action.UserSharedList;
import com.infinitiessoft.btrs.model.Role;
import com.infinitiessoft.btrs.model.User;
import com.infinitiessoft.btrs.model.UserShared;

@Name("authenticationManager")
public class AuthenticationManager {

	@Logger
	private Log log;
	@In
	EntityManager entityManager;
	@In(create = true)
	UserSharedList userSharedList;
	@In
	Credentials credentials;
	@In
	Identity identity;
	@In
	private PasswordManager passwordManager;
	
	
	@Transactional
	public boolean authenticate() {

		log.info("authenticating {0}", credentials.getUsername());
		try {
			UserShared userShared = userSharedList.getUserShared(credentials.getUsername());
			
			User user = (User) entityManager
					.createQuery("select distinct u from User u left join fetch u.roles where u.userSharedId = :userSharedId")
					.setParameter("userSharedId", userShared.getId())
					.getSingleResult();

			if (!validatePassword(credentials.getPassword(), userShared) || !userShared.getEnabled()) {
				return false;
			}

			for (Role role : user.getRoles()) {
				identity.addRole(role.getValue().name());
			}
			
			return true;
		}
		catch (NoResultException ex) {
			return false;
		}
	}
	
	private boolean validatePassword(String password, UserShared userShared) {
		return passwordManager.hash(password).equals(userShared.getPassword());
	}
	
	
}