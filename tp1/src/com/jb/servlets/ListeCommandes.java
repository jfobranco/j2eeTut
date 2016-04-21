package com.jb.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jb.beans.Customer;
import com.jb.beans.Order;

public class ListeCommandes extends HttpServlet {

	public static final String PARAM_ORDER = "order";
	public static final String ATT_SESSION_ORDERS = "orderlist";
	public static final String ATT_SESSION_CUSTOMERS = "customerlist";

	public static final String VIEW = "/WEB-INF/listerCommandes.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Affichage de la page d'inscription */
		HttpSession session = request.getSession();
		Map<Long, Order> orderList = (Map<Long, Order>) session.getAttribute(ATT_SESSION_ORDERS);
		if (orderList == null) {
			orderList = new HashMap<Long, Order>();

			Map<String, Customer> customerList = (Map<String, Customer>) session.getAttribute(ATT_SESSION_CUSTOMERS);
			if (customerList == null)
				customerList = new HashMap<String, Customer>();

			for (int i = 1; i < 10; i++) {
				Customer c = new Customer();
				String lastName = "last Name " + i;
				c.setLastName(lastName);
				c.setFirstName("first Name " + i);
				c.setAddress("address " + i);
				c.setPhone("100000" + i);
				c.setMail("aaa" + i + "@test.com");
				customerList.put(lastName, c);
				Order o = new Order();
				o.setDate(new Date(2016, 4, i));
				o.setCustomer(c);
				o.setAmount(100.0 + i);
				o.setPaymentMethod("aa" + i);
				o.setPaymentStatus("bb" + i);
				o.setDeliveryMode("cc" + i);
				o.setDeliveryStatus("dd" + i);
				orderList.put(o.getDate().getTime(), o);
			}
			session.setAttribute(ATT_SESSION_ORDERS, orderList);
			session.setAttribute(ATT_SESSION_CUSTOMERS, customerList);

		}
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderStr = request.getParameter(PARAM_ORDER);
		if (orderStr == null || orderStr.trim().length() == 0)
			orderStr = null;
		else
			orderStr = orderStr.trim();

		if (orderStr != null) {
			Long order = Long.decode(orderStr);

			HttpSession session = request.getSession();
			Map<Long, Order> orderList = (Map<Long, Order>) session.getAttribute(ATT_SESSION_ORDERS);
			if (orderList.containsKey(order)) {
				orderList.remove(order);
				session.setAttribute(ATT_SESSION_ORDERS, orderList);
			}
		}

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}