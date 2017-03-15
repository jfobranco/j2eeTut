package com.jb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.jb.entities.Profile;

@Stateless
public class ProfileDao {

	private static final String SQL_SELECT = "SELECT p FROM Profile p";

	@PersistenceContext(unitName = "hibernate_PU")
	private EntityManager em;

	public Profile findId(Long id) throws DAOException {
		Profile profile = em.find(Profile.class, id);

		return profile;
	}

	public Map<Long, Profile> list() throws DAOException {
		HashMap<Long, Profile> result = new HashMap<Long, Profile>();
		TypedQuery<Profile> query = em.createQuery(SQL_SELECT, Profile.class);
		try {
			List<Profile> profiles = query.getResultList();
			for (Profile profile : profiles)
				result.put(profile.getId(), profile);
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}

		return result;
	}

	public void create(Profile profile) throws DAOException {
		try {
			// save image
			em.persist(profile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	public void delete(Profile profile) throws DAOException {
		try {
			em.remove(profile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

}
