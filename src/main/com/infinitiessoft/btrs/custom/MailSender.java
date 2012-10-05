package com.infinitiessoft.btrs.logic;

import java.util.Map;
import java.util.Map.Entry;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.faces.Renderer;
import org.jboss.seam.log.Log;

@Name("mailSender")
@AutoCreate
public class MailSender {

	@Logger Log log;
	
	@In
	private Renderer renderer;
	
	private void sendEmail(String template, Map<String, Object> info) {
		try {
			for (Entry<String, Object> entry : info.entrySet()) {
				Contexts.getEventContext().set(entry.getKey(), entry.getValue());
			}
			renderer.render(template);
			log.info("Mail has been sent using template: #0", template);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
	}
	
	@Asynchronous
	public void sendSubmittedEmail(Map<String, Object> info) {
		sendEmail("/email/submitted.xhtml", info);
	}
	
	@Asynchronous
	public void sendReviewedEmail(Map<String, Object> info) {
		sendEmail("/email/reviewed.xhtml", info);
	}
}
