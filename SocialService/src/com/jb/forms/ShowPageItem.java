package com.jb.forms;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.jb.dao.PageItemDao;
import com.jb.entities.PageItem;

@ManagedBean
@ViewScoped
public class ShowPageItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private PageItem item;
	private Long pageItemId;
	private boolean disabled = true;
	@EJB
	private PageItemDao pageItemDao;

	public ShowPageItem() {
	}

	public Long getPageItemId() {
		return pageItemId;
	}

	public void setPageItemId(Long itemId) {
		this.pageItemId = itemId;
		getPageItem();
	}

	public PageItem getPageItem() {
		if (pageItemId != null && (item == null || item.getId() != pageItemId))
			item = pageItemDao.findId(pageItemId);

		return item;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
