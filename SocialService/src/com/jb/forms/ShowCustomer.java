package com.jb.forms;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.jb.dao.CustomerDao;
import com.jb.entities.Customer;
import com.jb.entities.Post;

@ManagedBean
@RequestScoped
public class ShowCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	// private int serviceType = 1;
	private Customer customer;
	private Long customerId;
	private boolean disabled = true;
	private Collection<Post> feed;

	@EJB
	private CustomerDao customerDao;

	public ShowCustomer() {
	}

	public Collection<Post> getFeed() {
		feed();

		return feed;
	}

	public void setFeed(Collection<Post> feed) {
		this.feed = feed;
	}

	public Collection<Post> feed() {
		Collection<Post> result = null;
		if (customer != null) {
			result = customerDao.feed(customer.getId());
			feed = result;
		}

		return result;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
		getCustomer();
	}

	public void load() {
		if (customerId != null)
			customer = customerDao.findId(customerId);
	}

	public Customer getCustomer() {
		if (customerId != null && (customer == null || customer.getId() != customerId))
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
