package com.jb.dao;

import com.jb.beans.Order;

public interface OrderDao {

	void create(Order order) throws DAOException;

	Order find(String date) throws DAOException;

	Order find(String date, Long customerId) throws DAOException;

	Order findId(String id) throws DAOException;

}