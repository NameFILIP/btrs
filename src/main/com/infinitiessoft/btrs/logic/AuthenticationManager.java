package com.infinitiessoft.btrs.logic;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import com.infinitiessoft.btrs.model.Role;
import com.infinitiessoft.btrs.model.User;

@Name("authenticationManager")
public class AuthenticationManager {

	@Logger
	private Log log;
	@In
	EntityManager entityManager;
	@In
	Credentials credentials;
	@In
	Identity identity;
	@In
	private PasswordManager passwordManager;
	
	@In(required = false)
	@Out(required = false, scope = ScopeType.SESSION)
	private User currentUser;
	
	@Transactional
	public boolean authenticate() {

		log.info("authenticating {0}", credentials.getUsername());
		try {
			
			User user = (User) entityManager
					.createQuery("select distinct u from User u left join fetch u.roles where u.username = :username")
					.setParameter("username", credentials.getUsername())
					.getSingleResult();

			if (!validatePassword(credentials.getPassword(), user) || !user.isEnabled()) {
				return false;
			}

			for (Role role : user.getRoles()) {
				identity.addRole(role.getValue().name());
			}
			
			currentUser = user;
			return true;
		}
		catch (NoResultException ex) {
			return false;
		}
	}
	
	public boolean validatePassword(String password, User user) {
		return passwordManager.hash(password).equals(user.getPassword());
	}
	
	
}