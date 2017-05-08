package com.jb.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Purchase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date date;
	private Customer client;
	// private double total;
	@ManyToOne(optional = false)
	@JoinColumn(name = "service", referencedColumnName = "id")
	private Service service;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "purchase")
	private List<PurchaseItem> items;

	public Purchase() {
		date = new Date();
	}

	public List<PurchaseItem> getItems() {
		return items;
	}

	public void setItems(List<PurchaseItem> items) {
		this.items = items;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Customer getClient() {
		return client;
	}

	public void setClient(Customer client) {
		this.client = client;
	}
}
