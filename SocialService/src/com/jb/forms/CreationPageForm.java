package com.jb.forms;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.jb.dao.PageDao;
import com.jb.dao.ServiceDao;
import com.jb.entities.Page;
import com.jb.entities.Service;

@ManagedBean
@RequestScoped
public class CreationPageForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private Page page;
	@EJB
	private PageDao pageDao;
	@EJB
	private ServiceDao serviceDao;
	private Long serviceId;

	public CreationPageForm() {
		page = new Page();
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
		if (serviceId != null) {
			Service service = serviceDao.findId(serviceId);
			page.setService(service);
			service.getPages().add(page);
		}
	}

	public Page createPage() {
		pageDao.create(page);
		FacesMessage message = new FacesMessage("Succès de création !");
		FacesContext.getCurrentInstance().addMessage(null, message);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("showService.xhtml?serviceId=" + serviceId);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return page;
	}

	public Page getPage() {
		return page;
	}
}
