package com.jb.forms;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.jb.Beans.SessionUtils;
import com.jb.dao.ServiceDao;
import com.jb.entities.Customer;
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

	public CreationServiceForm() {
		service = new Service();
		currentCustomer = SessionUtils.getUser();
	}

	public Service createService() {
		service.setOwner(currentCustomer);
		serviceDao.create(service);

		FacesMessage message = new FacesMessage("Service created !");
		FacesContext.getCurrentInstance().addMessage(null, message);

		return service;
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
