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
	}

	public void followService() {
		currentCustomer.addService(service);
		// Is it automatically persisted when changes are made?
		// userDao.save(currentCustomer);
		FacesMessage message = new FacesMessage("Service followed!");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public Service getService() {
		if (service == null)
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
