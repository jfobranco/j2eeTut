package com.jb.forms;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import org.jcp.xml.dsig.internal.dom.Utils;

import com.jb.Beans.SessionUtils;
import com.jb.dao.ImageDao;
import com.jb.dao.ProfileDao;
import com.jb.dao.ServiceDao;
import com.jb.entities.Customer;
import com.jb.entities.Image;
import com.jb.entities.Service;

@ManagedBean
@RequestScoped
public class CreationServiceForm implements Serializable {
	private static final long serialVersionUID = 1L;

	// private int serviceType = 1;
	private Service service;
	private Customer currentCustomer;
	@EJB
	private ServiceDao serviceDao;
	@EJB
	private ImageDao imageDao;
	@EJB
	private ProfileDao profileDao;
	// better have it here or in the profile? in profile need to see how to have properties that are not saved with entity
	private Part headerPicFile;
	private byte[] headerPicFileContent;
	private Part profilePicFile;
	private byte[] profilePicFileContent;

	public CreationServiceForm() {
		service = new Service();
		currentCustomer = SessionUtils.getUser();
	}

	public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		Part file = (Part) value;
		if (file.getSize() > 1024)
			msgs.add(new FacesMessage("file too big"));
		if (!"image/jpeg".equals(file.getContentType()))
			msgs.add(new FacesMessage("not a text file"));
		if (!msgs.isEmpty())
			throw new ValidatorException(msgs);
	}

	public Service createService() {
		// save images
		InputStream is = null;
		try {
			is = headerPicFile.getInputStream();
			headerPicFileContent = Utils.readBytesFromStream(is);
			is = profilePicFile.getInputStream();
			profilePicFileContent = Utils.readBytesFromStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		if (headerPicFileContent != null) {
			Image header = new Image();
			header.setImage(headerPicFileContent);
			imageDao.create(header);
			service.getProfile().setHeaderPic(header.getReference());
		}
		if (profilePicFileContent != null) {
			Image profile = new Image();
			profile.setImage(profilePicFileContent);
			imageDao.create(profile);
			service.getProfile().setProfilePic(profile.getReference());
		}
		profileDao.create(service.getProfile());
		service.setOwner(currentCustomer);
		serviceDao.create(service);

		FacesMessage message = new FacesMessage("Service created !");
		FacesContext.getCurrentInstance().addMessage(null, message);

		return service;
	}

	public Part getHeaderPicFile() {
		return headerPicFile;
	}

	public void setHeaderPicFile(Part headerPicFile) {
		this.headerPicFile = headerPicFile;
	}

	public Part getProfilePicFile() {
		return profilePicFile;
	}

	public void setProfilePicFile(Part profilePicFile) {
		this.profilePicFile = profilePicFile;
	}

	public Service getService() {
		return service;
	}

	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}
}
