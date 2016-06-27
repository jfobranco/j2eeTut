package com.jb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.jb.entities.User;

@Stateless
public class UserDao {

	private static final String SQL_SELECT = "SELECT u FROM User u";

	@PersistenceContext(unitName = "j2eetp_PU")
	private EntityManager em;

	public User findId(Long id) throws DAOException {
		User customer = em.find(User.class, id);

		return customer;
	}

	public Map<Long, User> list() throws DAOException {
		HashMap<Long, User> result = new HashMap<Long, User>();
		TypedQuery<User> query = em.createQuery(SQL_SELECT, User.class);
		try {
			List<User> users = query.getResultList();
			for (User user : users)
				result.put(user.getId(), user);
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}

		return result;
	}

	public void create(User user) throws DAOException {
		try {
			em.persist(user);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void delete(User user) throws DAOException {
		try {
			em.remove(user);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}
