package com.infinitiessoft.btrs.custom;

import java.util.Comparator;
import java.util.Map;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.infinitiessoft.btrs.model.User;
import com.infinitiessoft.btrs.model.UserShared;

@Name("userComparator")
public class UserComparator implements Comparator<User>{

	@In("#{userSharedList.allUsersShared}")
	private Map<Long, UserShared> allUsersShared;
	
	@Override
	public int compare(User u1, User u2) {
		UserShared u1Shared = allUsersShared.get(u1.getUserSharedId());
		UserShared u2Shared = allUsersShared.get(u2.getUserSharedId());
		return u1Shared.getFullName().compareTo(u2Shared.getFullName());
	}

}
