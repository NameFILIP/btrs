package com.infinitiessoft.btrs.logic;

import java.util.Comparator;

import com.infinitiessoft.btrs.model.User;

public class UserComparator implements Comparator<User>{

	    public int compare(User u1, User u2) {
	       return u1.getFullName().compareTo(u2.getFullName());
	    }
	
}
