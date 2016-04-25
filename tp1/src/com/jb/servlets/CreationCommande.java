package com.jb.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jb.beans.Customer;
import com.jb.beans.Order;
import com.jb.dao.CustomerDao;
import com.jb.dao.DAOFactory;
import com.jb.dao.OrderDao;
import com.jb.forms.CreationCommandeForm;

public class CreationCommande extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_FORM = "form";
	public static final String ATT_ORDER = "order";
	public static final String ATT_SESSION_ORDERS = "orderlist";
	public static final String ATT_SESSION_CUSTOMERS = "customerlist";

	public static final String VIEW = "/WEB-INF/creationCommande.jsp";
	public static final String VIEW2 = "/WEB-INF/afficherCommande.jsp";

	private CustomerDao customerDao;
	private OrderDao orderDao;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		DAOFactory factory = (DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY);
		this.customerDao = factory.getCustomerDao();
		this.orderDao = factory.getOrderDao();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Affichage de la page d'inscription */
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CreationCommandeForm form = new CreationCommandeForm(orderDao, customerDao);

		Order order = form.createOrder(request);

		HttpSession session = request.getSession();

		if (form.getErreurs().isEmpty()) {
			Map<Long, Order> orderList = (Map<Long, Order>) session.getAttribute(ATT_SESSION_ORDERS);
			if (orderList == null)
				orderList = new HashMap<Long, Order>();

			Map<String, Customer> customerList = (Map<String, Customer>) session.getAttribute(ATT_SESSION_CUSTOMERS);
			if (customerList == null)
				customerList = new HashMap<String, Customer>();

			orderList.put(order.getDate().getTime(), order);
			session.setAttribute(ATT_SESSION_ORDERS, orderList);

			if (!customerList.containsKey(order.getCustomer().getLastName())) {
				customerList.put(order.getCustomer().getLastName(), order.getCustomer());
				session.setAttribute(ATT_SESSION_CUSTOMERS, customerList);
			}
		}

		request.setAttribute(ATT_ORDER, order);
		request.setAttribute(ATT_FORM, form);

		/* Transmission de la paire d'objets request/response à notre JSP */
		this.getServletContext().getRequestDispatcher(form.getErreurs().isEmpty() ? VIEW2 : VIEW).forward(request,
				response);
	}
}
