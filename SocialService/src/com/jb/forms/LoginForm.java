package com.jb.forms;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.jb.Beans.SessionUtils;
import com.jb.dao.CustomerDao;
import com.jb.dao.PostDao;
import com.jb.entities.Customer;
import com.jb.entities.Post;

@ManagedBean
@SessionScoped
public class LoginForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private String pwd;
	private String msg;
	private String user;
	private Collection<Post> feed;

	@EJB
	private CustomerDao customerDao;
	@EJB
	private PostDao postDao;

	public LoginForm() {
		feed = feed();
	}

	public Collection<Post> getFeed() {
		feed();

		return feed;
	}

	public void setFeed(Collection<Post> feed) {
		this.feed = feed;
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

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}

			return "admin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Incorrect Username and Passowrd", "Please enter correct username and Password"));
			return "login";
		}
	}

	public Collection<Post> feed() {
		HttpSession session = SessionUtils.getSession();
		Customer user = session != null ? (Customer) session.getAttribute("user") : null;
		Collection<Post> result = null;
		if (user != null && customerDao != null)
			result = customerDao.feed(user.getId());
		else if (postDao != null)
			result = postDao.feed();

		feed = result;

		return result;
	}

	// logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "login";
	}
}
