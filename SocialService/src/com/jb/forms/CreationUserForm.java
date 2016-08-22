package com.jb.forms;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.jb.dao.CustomerDao;
import com.jb.entities.Customer;

@ManagedBean
@RequestScoped
public class CreationUserForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private Customer user;
	@EJB
	private CustomerDao userDao;

	public CreationUserForm() {
		user = new Customer();
	}

	public Customer createUser() {
		user.setInscriptionDate(new Timestamp(System.currentTimeMillis()));
		userDao.create(user);
		FacesMessage message = new FacesMessage("Succès de l'inscription !");
		FacesContext.getCurrentInstance().addMessage(null, message);

		return user;
	}

	public Customer getUser() {
		return user;
	}
}
