package com.jb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.jb.entities.Page;

@Stateless
public class PageDao {

	private static final String SQL_SELECT = "SELECT p FROM Page p";

	@PersistenceContext(unitName = "hibernate_PU")
	private EntityManager em;

	public Page findId(Long id) throws DAOException {
		Page post = em.find(Page.class, id);

		return post;
	}

	public Map<Long, Page> list() throws DAOException {
		HashMap<Long, Page> result = new HashMap<Long, Page>();
		TypedQuery<Page> query = em.createQuery(SQL_SELECT, Page.class);
		try {
			List<Page> pages = query.getResultList();
			for (Page page : pages)
				result.put(page.getId(), page);
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return result;
	}

	public void create(Page page) throws DAOException {
		try {
			em.persist(page);
			em.merge(page.getService());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	public void delete(Page page) throws DAOException {
		try {
			em.remove(page);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

}
