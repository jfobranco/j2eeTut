package com.jb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.jb.entities.Customer;

@Stateless
public class CustomerDao {

	private static final String SQL_SELECT = "SELECT c FROM Customer c";
	// private static final String SQL_SELECT_BY_NAME = "SELECT c FROM Customer
	// c WHERE c.lastName = :lastName";
	// private static final String SQL_SELECT_BY_FULLNAME = "SELECT c FROM
	// Customer c WHERE c.firstName = :firstName AND c.lastName = :lastName";

	@PersistenceContext(unitName = "j2eetp_PU")
	private EntityManager em;

	// public Customer find(String name, String firstName) throws DAOException {
	// Customer customer = null;
	// Query query = em.createQuery(SQL_SELECT_BY_FULLNAME);
	// query.setParameter("lastName", name);
	// query.setParameter("firstName", firstName);
	// try {
	// customer = (Customer) query.getSingleResult();
	// } catch (NoResultException e) {
	// return null;
	// } catch (Exception e) {
	// throw new DAOException(e);
	// }
	//
	// return customer;
	// }

	// public Customer find(String name) throws DAOException {
	// Customer customer = null;
	// Query query = em.createQuery(SQL_SELECT_BY_NAME);
	// query.setParameter("lastName", name);
	// try {
	// customer = (Customer) query.getSingleResult();
	// } catch (NoResultException e) {
	// return null;
	// } catch (Exception e) {
	// throw new DAOException(e);
	// }
	//
	// return customer;
	// }

	public Customer findId(Long id) throws DAOException {
		Customer customer = em.find(Customer.class, id);

		return customer;
	}

	public Map<Long, Customer> list() throws DAOException {
		HashMap<Long, Customer> result = new HashMap<Long, Customer>();
		TypedQuery<Customer> query = em.createQuery(SQL_SELECT, Customer.class);
		try {
			List<Customer> customers = query.getResultList();
			for (Customer customer : customers)
				result.put(customer.getId(), customer);
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}

		return result;
	}

	public void create(Customer customer) throws DAOException {
		try {
			em.persist(customer);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void delete(Customer customer) throws DAOException {
		try {
			em.remove(customer);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}
