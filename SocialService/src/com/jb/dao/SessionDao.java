package com.jb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.jb.entities.Purchase;
import com.jb.entities.PurchaseItem;
import com.jb.entities.Session;

@Stateless
public class SessionDao {

	private static final String SQL_SELECT = "SELECT s FROM Session s";

	@PersistenceContext(unitName = "hibernate_PU")
	private EntityManager em;

	public Session findId(Long id) throws DAOException {
		Session session = em.find(Session.class, id);

		return session;
	}

	// Add function to get sessions for customer and for service
	public Map<Long, Session> list() throws DAOException {
		HashMap<Long, Session> result = new HashMap<Long, Session>();
		TypedQuery<Session> query = em.createQuery(SQL_SELECT, Session.class);
		try {
			List<Session> sessions = query.getResultList();
			for (Session session : sessions)
				result.put(session.getId(), session);
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return result;
	}

	public void create(Session session) throws DAOException {
		try {
			em.persist(session);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	public void save(Session session, Purchase order, PurchaseItem item) throws DAOException {
		try {
			em.persist(order);
			em.persist(item);
			em.merge(session);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	public void delete(Session session) throws DAOException {
		try {
			em.remove(session);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

}
