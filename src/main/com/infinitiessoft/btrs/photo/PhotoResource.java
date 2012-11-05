package com.infinitiessoft.btrs.photo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Log;
import org.jboss.seam.servlet.ContextualHttpServletRequest;
import org.jboss.seam.ui.graphicImage.GraphicImageStore;
import org.jboss.seam.ui.graphicImage.GraphicImageStore.ImageWrapper;
import org.jboss.seam.web.AbstractResource;

@Scope(ScopeType.APPLICATION)
@Name("photoResource")
@Startup
@BypassInterceptors
public class PhotoResource extends AbstractResource implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Logger
	Log log;
	
	@Override
	public void getResource(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		// Wrap this, we need an ApplicationContext
		new ContextualHttpServletRequest(request) {
			@Override
			public void process() throws IOException {
				doWork(request, response);
			}
		}.run();
	}

	protected void doWork(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pathInfo = request.getPathInfo().substring(getResourcePath().length() + 1,
	               request.getPathInfo().lastIndexOf("."));
		
	      ImageWrapper image = GraphicImageStore.instance().get(pathInfo);
	      if (image != null && image.getImage() != null)
	      {
	         response.setContentType(image.getContentType().getMimeType());
	         response.setStatus(HttpServletResponse.SC_OK);
	         response.setContentLength(image.getImage().length);
	         ServletOutputStream os = response.getOutputStream();
	         os.write(image.getImage());
	         os.flush();
	      }
	      else
	      {
	         response.sendError(HttpServletResponse.SC_NOT_FOUND);
	      }
	}

	
	@Create
	public void init(){
		org.jboss.seam.core.Init.instance().addResourceProvider("photoResource");
	}
	
	
	@Override
	public String getResourcePath() {
		return "/photos";
	}

}
