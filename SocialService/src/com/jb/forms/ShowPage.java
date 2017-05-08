package com.jb.forms;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.jb.Beans.SessionUtils;
import com.jb.dao.PageDao;
import com.jb.dao.SessionDao;
import com.jb.entities.Customer;
import com.jb.entities.Page;
import com.jb.entities.PageItem;
import com.jb.entities.Purchase;
import com.jb.entities.PurchaseItem;
import com.jb.entities.Service;
import com.jb.entities.Session;

@ManagedBean
@ViewScoped
public class ShowPage implements Serializable {
	private static final long serialVersionUID = 1L;

	private Page page;
	private Long pageId;
	private boolean disabled = true;
	private Customer currentCustomer;
	@EJB
	private PageDao pageDao;
	@EJB
	private SessionDao sessionDao;
	private PageItem item = new PageItem();

	public ShowPage() {
		currentCustomer = SessionUtils.getUser();
	}

	public void addMenuItem() {
		String message = "";
		if (currentCustomer == null)
			message = "You must login";
		else if (item.getName() == null || item.getContent() == null)
			message = "Missing fields";
		else {
			item.setPage(page);
			page.getItems().add(item);
			pageDao.save(page, item);
			item = new PageItem();
			message = "Item added";
		}

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("showServicePage.xhtml?pageId=" + pageId);
		} catch (IOException e) {
			e.printStackTrace();
		}

		FacesMessage facesMessage = new FacesMessage(message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public void orderItem(PageItem item) {
		// Need to find current session
		// we should only save some sessions otherwise we would have all historic
		// have object for current sessions
		// Test if session valid
		Service service = page.getService();
		Session session = service.getSessions().get(0);
		Purchase purchase = session.getOrder();
		boolean newPurchase = false;
		if (purchase == null) {
			purchase = new Purchase();
			session.setOrder(purchase);
			purchase.setClient(currentCustomer);
			purchase.setService(service);
			newPurchase = true;
		}
		PurchaseItem purchaseItem = new PurchaseItem();
		purchaseItem.setPurchase(purchase);
		purchaseItem.setItem(item);
		sessionDao.save(session, newPurchase ? purchase : null, purchaseItem);
		String message = "Try to order " + item.getName();
		FacesMessage facesMessage = new FacesMessage(message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public PageItem getItem() {
		return item;
	}

	public void setItem(PageItem item) {
		this.item = item;
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
