package com.jb.dao;

import java.util.ArrayList;
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
import com.jb.entities.Post;
import com.jb.entities.Service;
import com.jb.entities.Session;

@Stateless
public class CustomerDao {

	private static final String SQL_SELECT = "SELECT c FROM Customer c";
	private static final String SQL_SELECT_GET = "SELECT c FROM Customer c WHERE c.mail=:m AND c.password=:p";
	private static final String SQL_SELECT_FEED = "SELECT p FROM Post p JOIN FETCH p.service s JOIN FETCH s.customer c WHERE c.id=:i ORDER BY p.date desc";

	@PersistenceContext(unitName = "hibernate_PU")
	private EntityManager em;

	public Customer findId(Long id) throws DAOException {
		Customer customer = em.find(Customer.class, id);

		return customer;
	}

	public List<Post> feed(Long userId) throws DAOException {
		List<Post> result = new ArrayList<Post>();
		TypedQuery<Post> query = em.createQuery(SQL_SELECT_FEED, Post.class);
		query.setParameter("i", userId);
		try {
			List<Post> posts = query.getResultList();
			for (Post post : posts)
				result.add(post);
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return result;
	}

	public Map<Long, Customer> list() throws DAOException {
		HashMap<Long, Customer> result = new HashMap<Long, Customer>();
		TypedQuery<Customer> query = em.createQuery(SQL_SELECT, Customer.class);
		try {
			List<Customer> users = query.getResultList();
			for (Customer user : users)
				result.put(user.getId(), user);
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
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
		em.merge(user);
		em.flush();
	}

	public void save(Customer user, Service service) {
		em.merge(user);
		em.merge(service);
		em.flush();
	}

	public void save(Customer user, Service service, Session session) {
		em.merge(user);
		em.merge(service);
		em.persist(session);
		em.flush();
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
		if (user == null || password == null)
			return null;

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
