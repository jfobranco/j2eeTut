package com.jb.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jb.beans.Customer;
import com.jb.dao.CustomerDao;
import com.jb.dao.DAOFactory;

public class ListeClients extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String PARAM_CUSTOMER = "customer";
	public static final String ATT_SESSION_CUSTOMERS = "customerlist";

	public static final String VIEW = "/WEB-INF/listerClients.jsp";

	private CustomerDao customerDao;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.customerDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCustomerDao();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customerStr = request.getParameter(PARAM_CUSTOMER);
		if (customerStr == null || customerStr.trim().length() == 0)
			customerStr = null;
		else
			customerStr = customerStr.trim();

		HttpSession session = request.getSession();
		Map<Long, Customer> customerList = (Map<Long, Customer>) session.getAttribute(ATT_SESSION_CUSTOMERS);

		Long customer = Long.decode(customerStr);
		if (customerList.containsKey(customer)) {
			Customer c = customerList.get(customer);
			Long id = c.getId();
			customerList.remove(customer);
			session.setAttribute(ATT_SESSION_CUSTOMERS, customerList);
			customerDao.delete(id);
		}

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
