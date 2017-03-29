package com.jb.forms;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.jb.Beans.SessionUtils;
import com.jb.dao.CustomerDao;
import com.jb.dao.ServiceDao;
import com.jb.entities.Customer;
import com.jb.entities.Service;
import com.jb.entities.Session;

@ManagedBean
@ViewScoped
public class ShowService implements Serializable {
	private static final long serialVersionUID = 1L;

	// private int serviceType = 1;
	private Service service;
	private Long serviceId;
	private boolean disabled = true;
	private boolean isFollow = false;
	private String followText = "";
	private String session = "";
	private Customer currentCustomer;
	@EJB
	private ServiceDao serviceDao;
	@EJB
	private CustomerDao userDao;
	private MapModel mapModel;

	public ShowService() {
		currentCustomer = SessionUtils.getUser();
		init();
	}

	public void init() {
		isFollow = currentCustomer != null && service != null && currentCustomer.getServices().contains(service) ? true
				: false;
		followText = isFollow ? "Unfollow" : "Follow";
	}

	public boolean isFollow() {
		return isFollow;
	}

	public void setFollow(boolean isFollow) {
		this.isFollow = isFollow;
	}

	public String getFollowText() {
		return followText;
	}

	public void setFollowText(String followText) {
		this.followText = followText;
	}

	public MapModel getMapModel() {
		return mapModel;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
		getService();
		init();
	}

	public void followService() {
		String message = null;

		if (currentCustomer == null)
			message = "You must login";
		else {
			boolean result = currentCustomer.handleService(service);
			service.handleCustomer(currentCustomer);
			userDao.save(currentCustomer, service);
			message = result ? "Service followed!" : "Service unfollowed";
			init();
		}
		FacesMessage facesMessage = new FacesMessage(message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public void logSession() {
		// Validate session code
		// Check if session code is available
		String message = null;

		if (currentCustomer == null)
			message = "You must login";
		else if (session == null)
			message = "You must enter a session code";
		else {
			if (!service.validateSession(session))
				message = "Invalid session";
			else {
				Session session = currentCustomer.logSession(service);
				userDao.save(currentCustomer, service, session);
				message = session != null ? "Session logged!" : "Problem logging session";
			}
		}
		session = null;
		FacesMessage facesMessage = new FacesMessage(message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public Service getService() {
		if (serviceId != null && (service == null || service.getId() != serviceId)) {
			service = serviceDao.findId(serviceId);
			if (service != null) {
				String coordsStr = service.getCoordinates();
				if (coordsStr != null) {
					mapModel = new DefaultMapModel();
					// Shared coordinates
					String[] coords = coordsStr.split(",");
					LatLng coord1 = new LatLng(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));

					mapModel.addOverlay(new Marker(coord1, service.getName()));
				}
			}
		}

		return service;
	}

	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}
}
