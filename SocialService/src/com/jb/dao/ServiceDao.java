package com.jb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.jb.entities.Service;

@Stateless
public class ServiceDao {

	private static final String SQL_SELECT = "SELECT s FROM Service s";

	@PersistenceContext(unitName = "hibernate_PU")
	private EntityManager em;

	public Service findId(Long id) throws DAOException {
		Service service = em.find(Service.class, id);

		return service;
	}

	public Map<Long, Service> list() throws DAOException {
		HashMap<Long, Service> result = new HashMap<Long, Service>();
		TypedQuery<Service> query = em.createQuery(SQL_SELECT, Service.class);
		try {
			List<Service> services = query.getResultList();
			for (Service service : services)
				result.put(service.getId(), service);
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return result;
	}

	public void save(Service service) {
		em.merge(service);
		em.flush();
	}

	public void create(Service service) throws DAOException {
		try {
			// save image
			em.persist(service);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	public void delete(Service service) throws DAOException {
		try {
			em.remove(service);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

}
