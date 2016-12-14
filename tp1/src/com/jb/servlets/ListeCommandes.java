package com.jb.servlets;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jb.dao.OrderHeaderDao;
import com.jb.entities.OrderHeader;

@WebServlet(urlPatterns = { "/listeCommandes" })
public class ListeCommandes extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String PARAM_ORDER = "order";
	public static final String ATT_SESSION_ORDERS = "orderlist";
	public static final String ATT_SESSION_CUSTOMERS = "customerlist";

	public static final String VIEW = "/WEB-INF/listerCommandes.jsp";

	@EJB
	private OrderHeaderDao orderDao;

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
			Map<Long, OrderHeader> orderList = (Map<Long, OrderHeader>) session.getAttribute(ATT_SESSION_ORDERS);
			if (orderList != null && order != null && orderList.containsKey(order)) {
				OrderHeader o = orderList.get(order);
				orderDao.delete(o);
				orderList.remove(order);
				session.setAttribute(ATT_SESSION_ORDERS, orderList);
			}
		}

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
