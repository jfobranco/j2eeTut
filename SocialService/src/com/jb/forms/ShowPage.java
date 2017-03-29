package com.jb.forms;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.jb.dao.PageDao;
import com.jb.entities.Page;

@ManagedBean
@RequestScoped
public class ShowPage implements Serializable {
	private static final long serialVersionUID = 1L;

	private Page page;
	private Long pageId;
	private boolean disabled = true;
	@EJB
	private PageDao pageDao;

	public ShowPage() {
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
		getPage();
	}

	public void load() {
		if (pageId != null)
			page = pageDao.findId(pageId);
	}

	public Page getPage() {
		if (pageId != null && (page == null || page.getId() != pageId))
			page = pageDao.findId(pageId);

		return page;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
