package com.jb.dao;

import static com.jb.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.jb.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jb.beans.Customer;

public class CustomerDaoImpl implements CustomerDao {

	private static final String SQL_SELECT_BY_NAME = "SELECT id, email, nom, mot_de_passe, date_inscription FROM Customer WHERE lastName = ?";
	private static final String SQL_SELECT_BY_FULLNAME = "SELECT id, email, nom, mot_de_passe, date_inscription FROM Customer WHERE firstName = ? AND lastName = ?";
	private static final String SQL_SELECT_BY_ID = "SELECT id, email, nom, mot_de_passe, date_inscription FROM Customer WHERE id = ?";
	private static final String SQL_INSERT = "INSERT INTO Customer (address, firstName, lastName, mail, phone) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM Customer WHERE id = ?";

	private DAOFactory daoFactory;

	CustomerDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public Customer find(String name, String firstName) throws DAOException {
		return find(SQL_SELECT_BY_FULLNAME, firstName, name);
	}

	public Customer find(String name) throws DAOException {
		return find(SQL_SELECT_BY_NAME, name);
	}

	public Customer findId(Long id) throws DAOException {
		return find(SQL_SELECT_BY_ID, id);
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

	public void delete(Long id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE, true, id);
			int statut = preparedStatement.executeUpdate();
			if (statut == 0)
				throw new DAOException("Échec de la suppression de l'utilisateur.");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}

	private Customer find(String sql, Object... objets) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Customer customer = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, sql, false, objets);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				customer = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return customer;
	}

	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	private static Customer map(ResultSet resultSet) throws SQLException {
		Customer customer = new Customer();
		customer.setId(resultSet.getLong("id"));
		customer.setAddress(resultSet.getString("address"));
		customer.setFirstName(resultSet.getString("firstName"));
		customer.setLastName(resultSet.getString("lastName"));
		customer.setMail(resultSet.getString("mail"));
		customer.setPhone(resultSet.getString("phone"));

		return customer;
	}

}
