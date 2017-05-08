package com.jb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.jb.entities.PageItem;

@Stateless
public class PageItemDao {

	private static final String SQL_SELECT = "SELECT p FROM PageItem p";

	@PersistenceContext(unitName = "hibernate_PU")
	private EntityManager em;

	public PageItem findId(Long id) throws DAOException {
		PageItem item = em.find(PageItem.class, id);

		return item;
	}

	public Map<Long, PageItem> list() throws DAOException {
		HashMap<Long, PageItem> result = new HashMap<Long, PageItem>();
		TypedQuery<PageItem> query = em.createQuery(SQL_SELECT, PageItem.class);
		try {
			List<PageItem> items = query.getResultList();
			for (PageItem item : items)
				result.put(item.getId(), item);
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return result;
	}

	public void create(PageItem item) throws DAOException {
		try {
			em.persist(item);
			em.merge(item.getPage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	public void save(PageItem item) {
		em.merge(item);
		em.flush();
	}

	public void delete(PageItem item) throws DAOException {
		try {
			em.remove(item);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

}
