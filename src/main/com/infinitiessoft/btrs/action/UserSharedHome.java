package com.infinitiessoft.btrs.action;

import java.util.Date;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.custom.PasswordManager;
import com.infinitiessoft.btrs.model.UserShared;

@Name("userSharedHome")
public class UserSharedHome extends EntityHome<UserShared> {

	private static final long serialVersionUID = 1L;
	
	@Override
    protected String getPersistenceContextName() {
         return "userEntityManager";
    }
	
	@Logger private Log log;

	@In
	PasswordManager passwordManager;
	
	@In
	UserHome userHome;
	
	public void setUserSharedId(Long id) {
		setId(id);
	}

	public Long getUserSharedId() {
		return (Long) getId();
	}

	@Override
	public UserShared getInstance() {
		Long userSharedId = userHome.getInstance().getUserSharedId();
		if (userSharedId != null) {
			setUserSharedId(userSharedId);
		}
		return super.getInstance();
	}
	
	@Override
	public String persist() {
		log.info("Registering user #{userShared.username}");
		UserShared userShared = getInstance();
		userShared.setPassword(passwordManager.hash(userShared.getPassword()));
		userShared.setCreatedDate(new Date());
		userShared.setEnabled(true);
		return super.persist();
	}

	@Override
	public String update() {
		UserShared userShared = getInstance();
		if (isPasswordChanged()) {
			log.debug("Changing #{userShared.username}'s password");
			userShared.setPassword(passwordManager.hash(userShared.getPassword()));
		}
		return super.update();
	}

	public boolean isPasswordChanged() {
		String oldPassword = (String) getEntityManager()
				.createQuery("select us.password from UserShared us where us.id = #{userShared.id}")
				.getSingleResult();
		return ! getInstance().getPassword().equals(oldPassword);
	}
	
}
