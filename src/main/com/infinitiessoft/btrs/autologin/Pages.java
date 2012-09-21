package com.infinitiessoft.btrs.autologin;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.core.Manager;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.NotLoggedInException;
import org.jboss.seam.util.Base64;


@Scope(ScopeType.APPLICATION)
@BypassInterceptors
@Name("org.jboss.seam.navigation.pages")
@Startup
public class Pages extends org.jboss.seam.navigation.Pages {

    @Override
	public void redirectToLoginView() {
		notLoggedIn();
		// Ensure that we haven't been authenticated as a result of the org.jboss.seam.security.notLoggedIn event
		if (!Identity.instance().isLoggedIn()) {
			String loginViewId = getLoginViewId();
			if (loginViewId == null) {
				throw new NotLoggedInException();
			} else {
				Manager.instance().redirect(loginViewId);
			}
		} else {
			StatusMessages.instance().clearGlobalMessages();
		}
    }
    
    public static void main(String[] args) {
    	String uname = "accountant";
    	String val = "-3dbba15e:139e33d8bc1:-7fca:7681835981748210560";
    	
    	String encoded = Base64.encodeBytes((uname + ":" + val).getBytes(), Base64.DONT_BREAK_LINES);
    	System.out.println("encoded: " + encoded);
    	String cookieValue = "YWRtaW46N2FmNTIzMTI6MTM5ZTZiNDk3Yzc6LTdmZTY6LTM1NDg3MzUzOTY3MzUwMDMzNjU";
    	System.out.println(Base64.decode(cookieValue));
    	String decoded = new String(Base64.decode(cookieValue));
        String username = decoded.substring(0, decoded.indexOf(':'));
        String value = decoded.substring(decoded.indexOf(':') + 1);
        System.out.println(decoded);
        System.out.println(username);
        System.out.println(value);
	}

}