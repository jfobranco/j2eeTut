package com.jb.forms;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import org.jcp.xml.dsig.internal.dom.Utils;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.jb.Beans.SessionUtils;
import com.jb.dao.ImageDao;
import com.jb.dao.ProfileDao;
import com.jb.dao.ServiceDao;
import com.jb.entities.Customer;
import com.jb.entities.Image;
import com.jb.entities.Service;

@ManagedBean
@ViewScoped
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
	private MapModel mapModel;

	public CreationServiceForm() {
		currentCustomer = SessionUtils.getUser();
		init();
	}

	public void init() {
		service = new Service();
		mapModel = new DefaultMapModel();
	}

	public void onGeocode(GeocodeEvent event) {
		List<GeocodeResult> results = event.getResults();

		if (results != null && !results.isEmpty()) {
			LatLng center = results.get(0).getLatLng();
			service.setCoordinates(center.getLat() + "," + center.getLng());

			for (int i = 0; i < results.size(); i++) {
				GeocodeResult result = results.get(i);
				mapModel.addOverlay(new Marker(result.getLatLng(), result.getAddress()));
			}
		}
	}

	public MapModel getMapModel() {
		return mapModel;
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		Part file = (Part) value;
		if (file.getSize() > 1024)
			msgs.add(new FacesMessage("file too big"));
		if (!"image/jpeg".equals(file.getContentType()))
			msgs.add(new FacesMessage("not a jpeg"));
		if (!msgs.isEmpty())
			throw new ValidatorException(msgs);
	}

	public Service createService() {
		if (currentCustomer == null) {
			FacesMessage message = new FacesMessage("You must login first !");
			FacesContext.getCurrentInstance().addMessage(null, message);

			return null;
		}
		InputStream is = null;
		try {
			if (headerPicFile != null) {
				is = headerPicFile.getInputStream();
				headerPicFileContent = Utils.readBytesFromStream(is);
			}
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
		profileDao.create(service.getProfile());
		service.setOwner(currentCustomer);
		serviceDao.create(service);

		FacesMessage message = new FacesMessage("Service created !");
		FacesContext.getCurrentInstance().addMessage(null, message);

		init();

		return service;
	}

	public Part getHeaderPicFile() {
		return headerPicFile;
	}

	public void setHeaderPicFile(Part headerPicFile) {
		this.headerPicFile = headerPicFile;
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
