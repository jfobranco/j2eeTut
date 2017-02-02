package com.jb.forms;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.jb.dao.PostDao;
import com.jb.dao.ServiceDao;
import com.jb.entities.Post;
import com.jb.entities.Service;

@ManagedBean
@RequestScoped
public class CreationPostForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private Post post;
	@EJB
	private PostDao postDao;
	@EJB
	private ServiceDao serviceDao;
	private Long serviceId;

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
		if (serviceId != null) {
			Service service = serviceDao.findId(serviceId);
			post.setService(service);
			service.getPosts().add(post);
		}
	}

	public CreationPostForm() {
		post = new Post();
		post.setDate(new Timestamp(System.currentTimeMillis()));
	}

	public Post createPost() {
		postDao.create(post);
		FacesMessage message = new FacesMessage("Succès de création !");
		FacesContext.getCurrentInstance().addMessage(null, message);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("showService.xhtml?serviceId=" + serviceId);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return post;
	}

	public Post getPost() {
		return post;
	}
}
