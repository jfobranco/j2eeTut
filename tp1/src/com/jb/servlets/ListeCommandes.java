package com.jb.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jb.beans.Order;

public class ListeCommandes extends HttpServlet {

	public static final String PARAM_ORDER = "order";
	public static final String ATT_SESSION_ORDERS = "orderlist";

	public static final String VIEW = "/WEB-INF/listerCommandes.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Affichage de la page d'inscription */
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderStr = request.getParameter(PARAM_ORDER);
		if (orderStr == null || orderStr.trim().length() == 0)
			orderStr = null;
		else
			orderStr = orderStr.trim();
		Long order = Long.decode(orderStr);

		System.out.println(order);

		HttpSession session = request.getSession();
		Map<String, Order> orderList = (Map<String, Order>) session.getAttribute(ATT_SESSION_ORDERS);
		if (orderList.containsKey(order)) {
			orderList.remove(order);
			session.setAttribute(ATT_SESSION_ORDERS, orderList);
		}

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
