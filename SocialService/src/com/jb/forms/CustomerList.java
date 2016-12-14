package com.jb.forms;

import java.io.Serializable;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.jb.dao.CustomerDao;
import com.jb.entities.Customer;

@ManagedBean
@RequestScoped
public class CustomerList implements Serializable {
	private static final long serialVersionUID = 1L;

	private Map<Long, Customer> customers;
	@EJB
	private CustomerDao customerDao;

	// add button to follow? no, add in service
	// create service page
	public CustomerList() {
	}

	public Map<Long, Customer> getCustomers() {
		if (customers == null)
			customers = customerDao.list();
		return customers;
	}

	public void setServices(Map<Long, Customer> customers) {
		this.customers = customers;
	}
}
