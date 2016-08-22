package com.jb.forms;

import java.io.Serializable;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.jb.dao.ServiceDao;
import com.jb.entities.Service;

@ManagedBean
@RequestScoped
public class ServiceList implements Serializable {
	private static final long serialVersionUID = 1L;

	private Map<Long, Service> services;
	@EJB
	private ServiceDao serviceDao;

	// add button to follow? no, add in service
	// create service page
	public ServiceList() {
	}

	public Map<Long, Service> getServices() {
		if (services == null)
			services = serviceDao.list();
		return services;
	}

	public void setServices(Map<Long, Service> services) {
		this.services = services;
	}
}
