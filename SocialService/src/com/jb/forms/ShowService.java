package com.jb.forms;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.jb.Beans.SessionUtils;
import com.jb.dao.CustomerDao;
import com.jb.dao.ServiceDao;
import com.jb.entities.Customer;
import com.jb.entities.Service;

@ManagedBean
@ViewScoped
public class ShowService implements Serializable {
	private static final long serialVersionUID = 1L;

	// private int serviceType = 1;
	private Service service;
	@ManagedProperty("#{param.serviceId}")
	private String serviceId;
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
		return Long.decode(serviceId);
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId.toString();
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
			service = serviceDao.findId(Long.decode(serviceId));
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
