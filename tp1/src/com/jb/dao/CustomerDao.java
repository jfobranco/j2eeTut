package com.jb.dao;

import com.jb.beans.Customer;

public interface CustomerDao {

	void create(Customer customer) throws DAOException;

	Customer find(String lastName) throws DAOException;

	Customer find(String firstName, String lastName) throws DAOException;

	Customer findId(Long id) throws DAOException;

}