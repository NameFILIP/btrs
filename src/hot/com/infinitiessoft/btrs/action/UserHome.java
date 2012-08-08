package com.infinitiessoft.btrs.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import com.infinitiessoft.btrs.logic.PasswordManager;
import com.infinitiessoft.btrs.model.Report;
import com.infinitiessoft.btrs.model.Role;
import com.infinitiessoft.btrs.model.StatusChange;
import com.infinitiessoft.btrs.model.User;

@Name("userHome")
public class UserHome extends EntityHome<User> {

	private static final long serialVersionUID = -5368319908832175365L;
	
	@Logger private Log log;

	@In(required = false)
	User currentUser;
	
	@In
	PasswordManager passwordManager;
	
	@In(create = true)
	RoleList roleList;
	
	public void setUserId(Long id) {
		setId(id);
	}

	public Long getUserId() {
		return (Long) getId();
	}

	@Override
	protected User createInstance() {
		User user = new User();
		user.setEnabled(true);
		return user;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
//		getInstance();
//		Department department = departmentHome.getDefinedInstance();
//		if (department != null) {
//			getInstance().setDepartment(department);
//		}
	}

	public boolean isWired() {
		return true;
	}

	public User getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Report> getIncomingReports() {
		return getInstance() == null ? null : new ArrayList<Report>(
				getInstance().getIncomingReports());
	}
	public List<Report> getOutgoingReports() {
		return getInstance() == null ? null : new ArrayList<Report>(
				getInstance().getOutgoingReports());
	}
	public List<StatusChange> getStatusChanges() {
		return getInstance() == null ? null : new ArrayList<StatusChange>(
				getInstance().getStatusChanges());
	}
	
	public List<Role> getRoles() {
		return getInstance() == null ? null : new ArrayList<Role>(getInstance().getRoles());
	}
	
	@Override
	public String persist() {
		log.info("Registering user #{user.username}");
		User user = getInstance();
		user.setPassword(passwordManager.hash(user.getPassword()));
		user.setCreatedDate(new Date());
		if (user.getRoles().isEmpty()) {
			user.addRole(roleList.getDefaultRole());
		}
		return super.persist();
	}

	@Override
	public String update() {
		User user = getInstance();
		if (isPasswordChanged()) {
			log.debug("Changing #{user.username}'s password");
			user.setPassword(passwordManager.hash(user.getPassword()));
		}
		return super.update();
	}
	
	public boolean isPasswordChanged() {
		String oldPassword = (String) getEntityManager()
				.createQuery("select u.password from User u where u.id = #{user.id}")
				.getSingleResult();
		return ! getInstance().getPassword().equals(oldPassword);
	}
	
	@Observer(Identity.EVENT_LOGIN_SUCCESSFUL)
	public void updateLastLoginDate() {
		currentUser.setLastLogin(new Date());
		setUserId(currentUser.getId());
		super.update();
	}
	
}
