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

import com.jb.entities.Post;

@Stateless
public class PostDao {

	private static final String SQL_SELECT = "SELECT p FROM Post p ORDER BY p.date desc";
	private static final String SQL_SELECT_SERVICE = "SELECT p FROM Post p WHERE p.serviceId=?";
	private static final String SQL_SELECT_CLIENT = "SELECT p FROM Post p JOIN Service s ON s.id=p.serviceId JOIN Customer_Service cs ON cs.serviceId=s.id AND cs.customer_ID=?";

	// Try to do join with default queries
	// try to correctly rename columns in join table

	@PersistenceContext(unitName = "hibernate_PU")
	private EntityManager em;

	public Post findId(Long id) throws DAOException {
		Post post = em.find(Post.class, id);

		return post;
	}

	public List<Post> feed() throws DAOException {
		List<Post> result = new ArrayList<Post>();
		TypedQuery<Post> query = em.createQuery(SQL_SELECT, Post.class);
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

	public Map<Long, Post> listPosts(String queryStr) throws DAOException {
		return listPosts(queryStr, null);
	}

	public Map<Long, Post> listPosts(String queryStr, Object[] params) throws DAOException {
		HashMap<Long, Post> result = new HashMap<Long, Post>();
		TypedQuery<Post> query = em.createQuery(queryStr, Post.class);
		try {
			List<Post> posts = query.getResultList();
			for (Post post : posts)
				result.put(post.getId(), post);
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return result;
	}

	public Map<Long, Post> list() throws DAOException {
		return listPosts(SQL_SELECT);
	}

	public Map<Long, Post> listService(Long id) throws DAOException {
		return listPosts(SQL_SELECT_SERVICE, new Object[] { id });
	}

	public Map<Long, Post> listClient(Long id) throws DAOException {
		return listPosts(SQL_SELECT_CLIENT, new Object[] { id });
	}

	public void create(Post post) throws DAOException {
		try {
			em.persist(post);
			em.merge(post.getService());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	public void delete(Post post) throws DAOException {
		try {
			em.remove(post);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

}
