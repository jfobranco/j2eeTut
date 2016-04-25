package com.jb.dao;

import static com.jb.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.jb.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jb.beans.Customer;
import com.jb.beans.Order;

public class OrderDaoImpl implements OrderDao {

	private static final String SQL_SELECT = "SELECT id, clientId, date, paymentMethod, paymentStatus, deliveryMode, deliveryStatus FROM j2eetp.order";
	private static final String SQL_SELECT_BY_DATE = "SELECT id, clientId, date, paymentMethod, paymentStatus, deliveryMode, deliveryStatus FROM j2eetp.order WHERE date = ?";
	private static final String SQL_SELECT_BY_DATECUSTOMER = "SELECT id, clientId, date, paymentMethod, paymentStatus, deliveryMode, deliveryStatus FROM j2eetp.order WHERE date = ? AND clientId = ?";
	private static final String SQL_SELECT_BY_ID = "SELECT id, clientId, date, paymentMethod, paymentStatus, deliveryMode, deliveryStatus FROM j2eetp.order WHERE id = ?";
	private static final String SQL_INSERT = "INSERT INTO j2eetp.order (clientId, date, paymentMethod, paymentStatus, deliveryMode, deliveryStatus) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM j2eetp.order WHERE id = ?";

	private DAOFactory daoFactory;

	OrderDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public Order find(Date date) throws DAOException {
		return trouver(SQL_SELECT_BY_DATE, date);
	}

	public Order find(Date date, Customer customer) throws DAOException {
		return trouver(SQL_SELECT_BY_DATECUSTOMER, date, customer.getId());
	}

	public Order findId(String id) throws DAOException {
		return trouver(SQL_SELECT_BY_ID, id);
	}

	public Map<Long, Order> list() throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		HashMap<Long, Order> result = new HashMap<Long, Order>();

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT, false);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Order order = map(resultSet);
				result.put(order.getId(), order);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return result;
	}

	public void create(Order order) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, order.getCustomer().getId(),
					order.getDate(), order.getPaymentMethod(), order.getPaymentStatus(), order.getDeliveryMode(),
					order.getDeliveryStatus());
			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");
			}
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				order.setId(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException("Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
		}
	}

	public void delete(Long id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE, true, id);
			int statut = preparedStatement.executeUpdate();
			if (statut == 0)
				throw new DAOException("Échec de la suppression de la commande.");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}

	private Order trouver(String sql, Object... objets) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Order order = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, sql, false, objets);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				order = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return order;
	}

	private Order map(ResultSet resultSet) throws SQLException {
		CustomerDaoImpl customer = new CustomerDaoImpl(daoFactory);
		Order order = new Order();
		order.setId(resultSet.getLong("id"));
		order.setCustomer(customer.findId(resultSet.getLong("clientId")));
		order.setDate(resultSet.getDate("date"));
		order.setPaymentMethod(resultSet.getString("paymentMethod"));
		order.setPaymentStatus(resultSet.getString("paymentStatus"));
		order.setDeliveryMode(resultSet.getString("deliveryMode"));
		order.setDeliveryStatus(resultSet.getString("deliveryStatus"));

		return order;
	}

}
