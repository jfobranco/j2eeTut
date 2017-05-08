package com.jb.forms;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.jb.dao.SessionDao;
import com.jb.entities.Session;

@ManagedBean
@RequestScoped
public class ShowSession implements Serializable {
	private static final long serialVersionUID = 1L;

	private Session session;
	private Long sessionId;
	private boolean disabled = true;
	@EJB
	private SessionDao sessionDao;

	public ShowSession() {
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
		getSession();
	}

	public void load() {
		if (sessionId != null)
			session = sessionDao.findId(sessionId);
	}

	public Session getSession() {
		if (sessionId != null && (session == null || session.getId() != sessionId))
			session = sessionDao.findId(sessionId);

		return session;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
