package com.jb.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	private Date creation;
	private String address;
	private String phone;
	private String mail;

	public Service() {
		creation = new Date();
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
}