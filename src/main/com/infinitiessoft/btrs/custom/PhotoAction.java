package com.infinitiessoft.btrs.custom;

import java.io.IOException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.ui.graphicImage.GraphicImageStore;
import org.jboss.seam.ui.graphicImage.GraphicImageStore.ImageWrapper;
import org.jboss.seam.ui.graphicImage.Image;

import com.infinitiessoft.btrs.action.ReportHome;
import com.infinitiessoft.btrs.model.Photo;
import com.infinitiessoft.btrs.model.Report;

@Name("photoAction")
@Scope(ScopeType.CONVERSATION)
public class PhotoAction {

	@Logger Log log;

	@In(create = true)
	ReportHome reportHome;
	
	private Photo photo;
	
	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	
	public void addPhotoToStore(Photo photo) {
		GraphicImageStore store = GraphicImageStore.instance();
		String fileName = photo.getFileName();
		String key = fileName.substring(0, fileName.lastIndexOf("."));
		
		if ( !store.contains(key)) {
			Image image = Image.instance();
			try {
				image.setInput(photo.getData());
				store.put(new ImageWrapper(image.getImage(), image.getContentType()), key);
			} catch (IOException e) {
				log.error("Error while adding photo " + fileName + " to store", e);
			}
		}
	}
	
	public void addPhotosToStore() {
		Report report = reportHome.getInstance();
		for (Photo photo : report.getPhotos()) {
			addPhotoToStore(photo);
		}
	}
	
}