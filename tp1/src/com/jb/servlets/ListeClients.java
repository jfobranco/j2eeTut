package com.jb.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jb.beans.Customer;

public class ListeClients extends HttpServlet {

	public static final String PARAM_CUSTOMER = "customer";
	public static final String ATT_SESSION_CUSTOMERS = "customerlist";

	public static final String VIEW = "/WEB-INF/listerClients.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customer = request.getParameter(PARAM_CUSTOMER);
		if (customer == null || customer.trim().length() == 0)
			customer = null;
		else
			customer = customer.trim();

		HttpSession session = request.getSession();
		Map<String, Customer> customerList = (Map<String, Customer>) session.getAttribute(ATT_SESSION_CUSTOMERS);

		if (customerList.containsKey(customer)) {
			customerList.remove(customer);
			session.setAttribute(ATT_SESSION_CUSTOMERS, customerList);
		}

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
