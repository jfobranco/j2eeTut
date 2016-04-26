package com.jb.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jb.beans.Order;
import com.jb.dao.DAOFactory;
import com.jb.dao.OrderDao;

public class ListeCommandes extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String PARAM_ORDER = "order";
	public static final String ATT_SESSION_ORDERS = "orderlist";
	public static final String ATT_SESSION_CUSTOMERS = "customerlist";

	public static final String VIEW = "/WEB-INF/listerCommandes.jsp";

	private OrderDao orderDao;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		DAOFactory factory = (DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY);
		this.orderDao = factory.getOrderDao();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			if (orderList != null && order != null && orderList.containsKey(order)) {
				orderList.remove(order);
				session.setAttribute(ATT_SESSION_ORDERS, orderList);
				orderDao.delete(order);
			}
		}

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
