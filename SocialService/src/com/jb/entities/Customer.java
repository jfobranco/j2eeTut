package com.jb.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private String mail;
	private String password;
	private Date inscriptionDate;
	// Services that the user follow
	@ManyToMany(mappedBy = "customer")
	private Collection<Service> service;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private List<Session> sessions;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile")
	private Profile profile;

	public Customer() {
		service = new ArrayList<Service>();
		profile = new Profile();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Customer && ((Customer) object).getId() == this.id)
			return true;
		else
			return false;
	}

	public boolean handleService(Service service) {
		if (this.service.contains(service)) {
			this.service.remove(service);
			return false;
		} else {
			this.service.add(service);
			return true;
		}
	}

	public Session logSession(Service service, String sessionCode) {
		Session session = new Session();
		session.setService(service);
		session.setCustomer(this);
		session.setCode(sessionCode);
		// session.setDuration(36E5);
		session.setValid(true);

		return session;
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

	public Date getInscriptionDate() {
		return inscriptionDate;
	}

	public void setInscriptionDate(Date inscriptionDate) {
		this.inscriptionDate = inscriptionDate;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
