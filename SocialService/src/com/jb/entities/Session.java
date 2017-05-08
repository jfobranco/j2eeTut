package com.jb.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Class responsible to join customer and service to create a session
 *
 * @author jfobranco
 *
 */
@Entity
public class Session {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date stamp;
	private String code;
	private int duration;
	private boolean valid;
	private Purchase order;
	@ManyToOne(optional = false)
	@JoinColumn(name = "service", referencedColumnName = "id")
	private Service service;
	@ManyToOne(optional = false)
	@JoinColumn(name = "client", referencedColumnName = "id")
	private Customer customer;

	public Session() {
		stamp = new Date();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Purchase getOrder() {
		return order;
	}

	public void setOrder(Purchase order) {
		this.order = order;
	}

}