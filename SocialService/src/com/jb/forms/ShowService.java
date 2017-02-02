package com.jb.forms;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.jb.Beans.SessionUtils;
import com.jb.dao.CustomerDao;
import com.jb.dao.ServiceDao;
import com.jb.entities.Customer;
import com.jb.entities.Service;

@ManagedBean
// @ViewScoped
@RequestScoped
public class ShowService implements Serializable {
	private static final long serialVersionUID = 1L;

	// private int serviceType = 1;
	private Service service;
	private Long serviceId;
	private boolean disabled = true;
	private Customer currentCustomer;
	@EJB
	private ServiceDao serviceDao;
	@EJB
	private CustomerDao userDao;

	public ShowService() {
		currentCustomer = SessionUtils.getUser();
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
		getService();
	}

	public void followService() {
		String message = null;

		if (currentCustomer == null)
			message = "You must login";
		else {
			boolean result = currentCustomer.addService(service);
			if (result) {
				result = service.addCustomer(currentCustomer);
				if (result)
					userDao.save(currentCustomer, service);
			}
			message = result ? "Service followed!" : "Service already followed";
		}

		FacesMessage facesMessage = new FacesMessage(message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public Service getService() {
		if (serviceId != null && (service == null || service.getId() != serviceId))
			service = serviceDao.findId(serviceId);

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
}
