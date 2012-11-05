package com.infinitiessoft.btrs.auth.autologin;

import static org.jboss.seam.ScopeType.SESSION;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.util.Hex;

@Name("org.jboss.seam.security.rememberMe")
@Scope(SESSION)
@BypassInterceptors
public class RememberMe extends org.jboss.seam.security.RememberMe {

	private static final long serialVersionUID = 1L;

	Log log = Logging.getLog(RememberMe.class);
	
	@Override
	protected String encodeToken(String username, String value) {
		StringBuilder sb = new StringBuilder();
		sb.append(username);
		sb.append(":");
		sb.append(value);
		return new String(Hex.encodeHex(sb.toString().getBytes()));
	}

	@Override
	@Observer(Credentials.EVENT_INIT_CREDENTIALS)
	public void initCredentials(Credentials credentials) {
		if (getMode().equals(Mode.usernameOnly)) {
			super.initCredentials(credentials);
		} else if (getMode().equals(Mode.autoLogin)) {
			
			String token = getCookieValue();
			if (token != null) {
				setEnabled(true);
				
				String cookiePath = getCookiePath();
				if (cookiePath != null) {
					setCookiePath(cookiePath);
				}
				
				HexDecodedToken decoded = new HexDecodedToken(token);
				if (getTokenStore().validateToken(decoded.getUsername(), decoded.getValue())) {
					credentials.setUsername(decoded.getUsername());
					credentials.setPassword(decoded.getValue());
				} else {
					getTokenStore().invalidateAll(decoded.getUsername());
				}
			}
		}
	}

	@Override
	@Observer(Identity.EVENT_POST_AUTHENTICATE)
	public void postAuthenticate(Identity identity) {
		String cookiePath = getCookiePath();
		if (cookiePath != null) {
			setCookiePath(cookiePath);
		}
		
		if (getMode().equals(Mode.autoLogin)) {
			HexDecodedToken decoded = new HexDecodedToken(getCookieValue());

			// Invalidate the current token (if it exists) whether enabled or not
			if (decoded.getUsername() != null) {
				getTokenStore().invalidateToken(decoded.getUsername(), decoded.getValue());
			}
		}
		super.postAuthenticate(identity);

	}

	private class HexDecodedToken {
		private String username;
		private String value;

		public HexDecodedToken(String cookieValue) {
			if (cookieValue != null) {
				try {
					String decoded = new String(Hex.decodeHex(cookieValue.toCharArray()));
					username = decoded.substring(0, decoded.indexOf(':'));
					value = decoded.substring(decoded.indexOf(':') + 1);
				} catch (Exception ex) {
					// swallow
				}
			}
		}

		public String getUsername() {
			return username;
		}

		public String getValue() {
			return value;
		}
	}

	private String getCookieValue() {
		Cookie cookie = getCookie();
		return cookie == null ? null : cookie.getValue();
	}

	private Cookie getCookie() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (ctx != null) {
			return (Cookie) ctx.getExternalContext().getRequestCookieMap().get(getCookieName());
		} else {
			return null;
		}
	}

	public String getCookieName() {
		return "org.jboss.seam.security.authtoken";
	}
	
	private void setCookiePath(String cookiePath) {
		Field tokenSelectorField;
		try {
			tokenSelectorField = this.getClass().getSuperclass().getDeclaredField("tokenSelector");
			
			tokenSelectorField.setAccessible(true);
			Method setCookiePathMethod = tokenSelectorField.getType()
					.getSuperclass().getSuperclass().getDeclaredMethod("setCookiePath", String.class);
			setCookiePathMethod.invoke(tokenSelectorField.get(this), cookiePath);
		} catch (Exception e) {
			log.error("Setting cookie path(#0) for Remember Me is broken", cookiePath);
		}
	}
}
