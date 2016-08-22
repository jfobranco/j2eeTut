package com.jb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.jb.entities.Customer;

@Stateless
public class CustomerDao {

	private static final String SQL_SELECT = "SELECT c FROM Customer c";
	private static final String SQL_SELECT_GET = "SELECT c FROM Customer c WHERE c.mail=:m AND c.password=:p";

	@PersistenceContext(unitName = "hibernate_PU")
	private EntityManager em;

	public Customer findId(Long id) throws DAOException {
		Customer customer = em.find(Customer.class, id);

		return customer;
	}

	public Map<Long, Customer> list() throws DAOException {
		HashMap<Long, Customer> result = new HashMap<Long, Customer>();
		TypedQuery<Customer> query = em.createQuery(SQL_SELECT, Customer.class);
		try {
			List<Customer> users = query.getResultList();
			for (Customer user : users)
				result.put(user.getId(), user);
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}

		return result;
	}

	public void create(Customer user) {
		try {
			em.persist(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(Customer user) {
		// try {
		// em.(user);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	@Transactional
	public void delete(Customer user) throws DAOException {
		try {
			em.remove(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	public Customer validate(String user, String password) {
		Customer customer = null;
		try {
			TypedQuery<Customer> query = em.createQuery(SQL_SELECT_GET, Customer.class);
			query.setParameter("m", user);
			query.setParameter("p", password);
			customer = query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customer;
	}
}
