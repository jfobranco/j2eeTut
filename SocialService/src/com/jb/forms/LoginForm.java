package com.jb.forms;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.jb.Beans.SessionUtils;
import com.jb.dao.CustomerDao;
import com.jb.entities.Customer;

@ManagedBean
@SessionScoped
public class LoginForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private String pwd;
	private String msg;
	private String user;
	@EJB
	private CustomerDao customerDao;

	public LoginForm() {
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	// validate login
	public String validateUsernamePassword() {
		Customer customer = customerDao.validate(user, pwd);
		if (customer != null) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("user", customer);

			FacesMessage message = new FacesMessage("User logged in !");
			FacesContext.getCurrentInstance().addMessage(null, message);

			return "admin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Incorrect Username and Passowrd", "Please enter correct username and Password"));
			return "login";
		}
	}

	// logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "login";
	}
}
