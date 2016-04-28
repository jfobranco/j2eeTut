package com.jb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.jb.entities.OrderHeader;

@Stateless
public class OrderHeaderDao {

	private static final String SQL_SELECT = "SELECT o FROM OrderHeader o";
	// private static final String SQL_SELECT_BY_DATE = "SELECT o FROM
	// OrderHeader o
	// WHERE o.date = :date";
	// private static final String SQL_SELECT_BY_DATECUSTOMER = "SELECT o FROM
	// OrderHeader o WHERE o.date = :date AND o.clientId = :clientId";

	@PersistenceContext(unitName = "j2eetp_PU")
	private EntityManager em;

	// public Order find(Date date) throws DAOException {
	// Order order = null;
	// Query query = em.createQuery(SQL_SELECT_BY_DATE);
	// query.setParameter("date", date);
	// try {
	// order = (Order) query.getSingleResult();
	// } catch (NoResultException e) {
	// return null;
	// } catch (Exception e) {
	// throw new DAOException(e);
	// }
	//
	// return order;
	// }
	//
	// public Order find(Date date, Customer customer) throws DAOException {
	// Order order = null;
	// Query query = em.createQuery(SQL_SELECT_BY_DATECUSTOMER);
	// query.setParameter("date", date);
	// query.setParameter("clientId", customer.getId());
	// try {
	// order = (Order) query.getSingleResult();
	// } catch (NoResultException e) {
	// return null;
	// } catch (Exception e) {
	// throw new DAOException(e);
	// }
	//
	// return order;
	// }

	public OrderHeader findId(String id) throws DAOException {
		OrderHeader order = em.find(OrderHeader.class, id);

		return order;
	}

	public Map<Long, OrderHeader> list() throws DAOException {
		HashMap<Long, OrderHeader> result = new HashMap<Long, OrderHeader>();
		TypedQuery<OrderHeader> query = em.createQuery(SQL_SELECT, OrderHeader.class);
		try {
			List<OrderHeader> orders = query.getResultList();
			for (OrderHeader order : orders)
				result.put(order.getId(), order);
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}

		return result;
	}

	public void create(OrderHeader order) throws DAOException {
		try {
			em.persist(order);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void delete(OrderHeader order) throws DAOException {
		try {
			em.remove(order);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

}
