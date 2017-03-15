package com.jb.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.jb.entities.Image;

@Stateless
public class ImageDao {

	private static final String SQL_SELECT_GET = "SELECT i FROM Image i WHERE i.reference=:ref";

	@PersistenceContext(unitName = "hibernate_PU")
	private EntityManager em;

	public Image findId(Long id) throws DAOException {
		Image customer = em.find(Image.class, id);

		return customer;
	}

	public Image findReference(String ref) throws DAOException {
		Image image = null;
		try {
			TypedQuery<Image> query = em.createQuery(SQL_SELECT_GET, Image.class);
			query.setParameter("ref", ref);
			image = query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return image;
	}

	public void create(Image image) {
		try {
			em.persist(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(Image image) {
		// em.persist(user);
		em.merge(image);
		// em.merge(user.getServices());
		em.flush();
		// try {
		// em.(user);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	@Transactional
	public void delete(Image image) throws DAOException {
		try {
			em.remove(image);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}
}
