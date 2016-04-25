package com.jb.dao;

import java.util.Map;

import com.jb.beans.Customer;

public interface CustomerDao {

	void create(Customer customer) throws DAOException;

	void delete(Long customer) throws DAOException;

	Customer find(String lastName) throws DAOException;

	Customer find(String firstName, String lastName) throws DAOException;

	Customer findId(Long id) throws DAOException;

	Map<Long, Customer> list() throws DAOException;

}