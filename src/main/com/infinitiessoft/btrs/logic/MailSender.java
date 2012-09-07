package com.infinitiessoft.btrs.logic;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.faces.Renderer;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.model.Report;

@Name("mailSender")
@AutoCreate
public class MailSender {

	@Logger Log log;
	
	@In
	private Renderer renderer;
	
	
	private void sendEmail(Report report, String template, String urlBase) {
		try {
			Contexts.getEventContext().set("report", report);
			Contexts.getEventContext().set("urlBase", urlBase);
			renderer.render(template);
			log.info("Mail has been sent using template: #0", template);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
	}
	
	@Asynchronous
	public void sendSubmittedEmail(Report report, String urlBase) {
		sendEmail(report, "/email/submitted.xhtml", urlBase);
	}
	
	@Asynchronous
	public void sendReviewedEmail(Report report, String urlBase) {
		sendEmail(report, "/email/reviewed.xhtml", urlBase);
	}
}
