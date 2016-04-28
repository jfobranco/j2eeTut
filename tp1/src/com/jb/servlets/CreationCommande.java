package com.jb.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jb.dao.CustomerDao;
import com.jb.dao.OrderHeaderDao;
import com.jb.entities.Customer;
import com.jb.entities.OrderHeader;
import com.jb.forms.CreationCommandeForm;

@WebServlet(urlPatterns = { "/creationCommande" })
public class CreationCommande extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_FORM = "form";
	public static final String ATT_ORDER = "order";
	public static final String ATT_SESSION_ORDERS = "orderlist";
	public static final String ATT_SESSION_CUSTOMERS = "customerlist";

	public static final String VIEW = "/WEB-INF/creationCommande.jsp";
	public static final String VIEW2 = "/WEB-INF/afficherCommande.jsp";

	@EJB
	private CustomerDao customerDao;
	@EJB
	private OrderHeaderDao orderDao;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Affichage de la page d'inscription */
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CreationCommandeForm form = new CreationCommandeForm(orderDao, customerDao);

		OrderHeader order = form.createOrder(request);

		request.setAttribute(ATT_ORDER, order);
		request.setAttribute(ATT_FORM, form);

		if (form.getErreurs().isEmpty()) {
			HttpSession session = request.getSession();

			Map<Long, OrderHeader> orderList = (Map<Long, OrderHeader>) session.getAttribute(ATT_SESSION_ORDERS);
			if (orderList == null)
				orderList = new HashMap<Long, OrderHeader>();

			Map<Long, Customer> customerList = (Map<Long, Customer>) session.getAttribute(ATT_SESSION_CUSTOMERS);
			if (customerList == null)
				customerList = new HashMap<Long, Customer>();

			orderList.put(order.getId(), order);
			session.setAttribute(ATT_SESSION_ORDERS, orderList);

			if (!customerList.containsKey(order.getCustomer().getId())) {
				customerList.put(order.getCustomer().getId(), order.getCustomer());
				session.setAttribute(ATT_SESSION_CUSTOMERS, customerList);
			}
		}

		/* Transmission de la paire d'objets request/response à notre JSP */
		this.getServletContext().getRequestDispatcher(form.getErreurs().isEmpty() ? VIEW2 : VIEW).forward(request,
				response);
	}
}
