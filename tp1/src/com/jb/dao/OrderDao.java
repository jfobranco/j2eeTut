package com.jb.dao;

import java.util.Date;
import java.util.Map;

import com.jb.beans.Customer;
import com.jb.beans.Order;

public interface OrderDao {

	void create(Order order) throws DAOException;

	void delete(Long id) throws DAOException;

	Order find(Date date) throws DAOException;

	Order find(Date date, Customer customer) throws DAOException;

	Order findId(String id) throws DAOException;

	Map<Long, Order> list() throws DAOException;

}