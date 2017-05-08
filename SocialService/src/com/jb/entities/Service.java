package com.jb.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Service {
	// Service types
	public static int SERVICE_TYPE_RESTAURANT = 1;

	// Service fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "ownerId")
	private Customer owner;
	private String name;
	private Date creation = new Date();
	private String address;
	private String coordinates;
	private String phone;
	private String mail;
	// Session codes that can be used to create session, should be changed every x time (day, hour ?)
	private List<String> sessionCodes;
	// Services that the user follow
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
	private List<Post> posts;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
	private List<Page> pages;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
	private List<Session> sessions;
	@ManyToMany
	private Collection<Customer> customer;
	private Profile profile = new Profile();

	public Service() {
	}

	public boolean validateSession(String session) {
		if (session == null || session == "")
			return false;

		return sessionCodes != null && sessionCodes.contains(session) ? true : false;
	}

	public void generateSessionCodes() {
		if (sessionCodes == null)
			sessionCodes = new ArrayList<String>();
		sessionCodes.add("1234");
		// need last generation date? or do it with some cron?
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public Collection<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(Collection<Customer> customer) {
		this.customer = customer;
	}

	public List<String> getSessionCodes() {
		return sessionCodes;
	}

	public void setSessionCodes(List<String> sessionCodes) {
		this.sessionCodes = sessionCodes;
	}

	public boolean handleCustomer(Customer customer) {
		if (this.customer.contains(customer)) {
			this.customer.remove(customer);
			return false;
		} else {
			this.customer.add(customer);
			return true;
		}
	}

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Service && ((Service) object).getId() == this.id)
			return true;
		else
			return false;
	}

	public static Service createService(int type) {
		Service result = null;
		if (type == SERVICE_TYPE_RESTAURANT)
			result = new Restaurant();
		result.creation = new Date();

		return result;
	}

	public Customer getOwner() {
		return owner;
	}

	public void setOwner(Customer owner) {
		this.owner = owner;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}