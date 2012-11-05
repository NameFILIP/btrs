package com.infinitiessoft.btrs.custom;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import com.infinitiessoft.btrs.action.ReportHome;
import com.infinitiessoft.btrs.model.Photo;
import com.infinitiessoft.btrs.model.Report;

@Name("uploadAction")
public class UploadAction {

	@Logger Log log;

	private static final int SCALED_WIDTH = 640;
	private static final int SCALED_HEIGHT = 480;
	
	@In
	ReportHome reportHome;

    private byte[] data;
    private String contentType;
    private String fileName;
    private String title;
    private int size;
    
    public ReportHome getReportHome() {
		return reportHome;
	}

	public void setReportHome(ReportHome reportHome) {
		this.reportHome = reportHome;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	
	public void upload() {
		byte[] scaledData = scale(data);
		if (scaledData != null) {
			Report report = reportHome.getInstance();

			Photo photo = new Photo();
			photo.setContentType(contentType);
			photo.setFileName(fileName);
			photo.setTitle(CustomUtils.isBlank(title) ? fileName : title);
			photo.setUploadDate(new Date());
			photo.setData(scaledData);
			photo.setSize(scaledData.length);
			photo.setReport(report);

			report.addPhoto(photo);
		} else {
			log.warn("Uploading a picture, but no file was added. Title: #0", title);
		}
    }
	
	private byte[] scale(byte[] originalPhoto) {
		InputStream original = new ByteArrayInputStream(originalPhoto);
		try {
			BufferedImage bufferedOriginal = ImageIO.read(original);
			BufferedImage scaled = Scalr.resize(bufferedOriginal, SCALED_WIDTH, SCALED_HEIGHT);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(scaled, "png", baos);
	        baos.flush();
	        byte[] scaledPhoto = baos.toByteArray();
	        baos.close();
	        
			return scaledPhoto;
		} catch (Exception e) {
			log.error("Error while scaling image " + fileName, e);
		}
		return null;
	}
	
}
