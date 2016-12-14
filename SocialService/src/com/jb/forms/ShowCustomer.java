package com.jb.forms;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.jb.dao.CustomerDao;
import com.jb.entities.Customer;

@ManagedBean
@ViewScoped
public class ShowCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	// private int serviceType = 1;
	private Customer customer;
	private Long customerId;
	private boolean disabled = true;
	@EJB
	private CustomerDao customerDao;

	public ShowCustomer() {
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Customer getCustomer() {
		if (customer == null && customerId != null)
			customer = customerDao.findId(customerId);
		return customer;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
