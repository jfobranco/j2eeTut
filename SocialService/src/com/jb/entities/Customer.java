package com.jb.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Customer {
	public enum Role {
		User, Owner
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private String mail;
	private String password;
	private Timestamp inscriptionDate;
	// Services that the user follow
	@ManyToMany(mappedBy = "customer")
	private Collection<Service> service;

	public Customer() {
		service = new ArrayList<Service>();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Customer && ((Customer) object).getId() == this.id)
			return true;
		else
			return false;
	}

	public boolean addService(Service service) {
		if (this.service.contains(service))
			return false;
		this.service.add(service);

		return true;
	}

	public Collection<Service> getServices() {
		return service;
	}

	public void setServices(List<Service> services) {
		this.service = services;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getInscriptionDate() {
		return inscriptionDate;
	}

	public void setInscriptionDate(Timestamp inscriptionDate) {
		this.inscriptionDate = inscriptionDate;
	}
}
