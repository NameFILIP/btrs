package com.infinitiessoft.btrs.custom;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.infinitiessoft.btrs.model.User;

@Name("errorAction")
@Scope(ScopeType.EVENT)
public class ErrorAction implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@In(value = "org.jboss.seam.handledException")
    private Exception e;
	
	private String developerMail;
	
	@In("#{messages['error.mail.subject']}")
	String errorMailSubject;
 
	@In(required = false)
	User currentUser;
	
    @In
    private MailSender mailSender;
 
    public void sendMail() {
		Map<String, Object> info = new HashMap<String, Object>();
		
		info.put("developerMail", developerMail);
		info.put("subject", errorMailSubject);

		info.put("currentUser", currentUser);

//		FacesContext context = FacesContext.getCurrentInstance();
//		if (context != null) {
//			String viewId = Pages.getViewId(context);
//			info.put("viewId", viewId);
//
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			parameters.putAll(context.getExternalContext().getRequestParameterMap());
//			parameters.putAll(Pages.instance().getStringValuesFromPageContext(context));
//			info.put("parameters", parameters);
//		}
		
		String stackTrace = null;
		if (e != null) {
			StringWriter content = new StringWriter();
			e.printStackTrace(new PrintWriter(content));
			stackTrace = content.getBuffer().toString();
		}
		info.put("stackTrace", stackTrace);
		
		mailSender.sendErrorMail(info);
	}
    
	public String getDeveloperMail() {
		return developerMail;
	}

	public void setDeveloperMail(String developerMail) {
		this.developerMail = developerMail;
	}

}