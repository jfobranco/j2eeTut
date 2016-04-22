package com.jb.dao;

import static com.jb.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.jb.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jb.beans.Customer;
import com.jb.beans.Order;

public class OrderDaoImpl implements CustomerDao {

	private static final String SQL_SELECT_BY_NAME = "SELECT id, email, nom, mot_de_passe, date_inscription FROM Utilisateur WHERE lastName = ?";
	private static final String SQL_SELECT_BY_FULLNAME = "SELECT id, email, nom, mot_de_passe, date_inscription FROM Utilisateur WHERE firstName = ? AND lastName = ?";
	private static final String SQL_SELECT_BY_ID = "SELECT id, email, nom, mot_de_passe, date_inscription FROM Utilisateur WHERE id = ?";
	private static final String SQL_INSERT = "INSERT INTO Customer (address, firstName, lastName, mail, phone) VALUES (?, ?, ?, ?, ?)";

	private DAOFactory daoFactory;

	OrderDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public Customer find(String name, String firstName) throws DAOException {
		return trouver(SQL_SELECT_BY_FULLNAME, firstName, name);
	}

	public Customer find(String name) throws DAOException {
		return trouver(SQL_SELECT_BY_NAME, name);
	}

	public Customer findId(String id) throws DAOException {
		return trouver(SQL_SELECT_BY_ID, id);
	}

	@Override
	public void create(Customer customer) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, customer.getAddress(),
					customer.getFirstName(), customer.getLastName(), customer.getMail(), customer.getPhone());
			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");
			}
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				customer.setId(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException("Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
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

	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
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
