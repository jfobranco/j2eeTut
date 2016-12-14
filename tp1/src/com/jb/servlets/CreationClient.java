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
import com.jb.entities.Customer;
import com.jb.forms.CreationClientForm;

@WebServlet(urlPatterns = { "/creationClient" })
public class CreationClient extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_CUSTOMER = "customer";
	public static final String ATT_FORM = "form";
	public static final String ATT_SESSION_CUSTOMERS = "customerlist";

	public static final String VIEW = "/WEB-INF/creationClient.jsp";
	public static final String VIEW2 = "/WEB-INF/afficherClient.jsp";

	@EJB
	private CustomerDao customerDao;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Affichage de la page d'inscription */
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CreationClientForm form = new CreationClientForm(customerDao);

		Customer customer = form.createCustomer(request);

		request.setAttribute(ATT_FORM, form);
		request.setAttribute(ATT_CUSTOMER, customer);

		if (form.getErreurs().isEmpty()) {
			HttpSession session = request.getSession();
			Map<Long, Customer> customerList = (Map<Long, Customer>) session.getAttribute(ATT_SESSION_CUSTOMERS);
			if (customerList == null)
				customerList = new HashMap<Long, Customer>();

			customerList.put(customer.getId(), customer);
			session.setAttribute(ATT_SESSION_CUSTOMERS, customerList);
		}

		/* Transmission de la paire d'objets request/response à notre JSP */
		this.getServletContext().getRequestDispatcher(form.getErreurs().isEmpty() ? VIEW2 : VIEW).forward(request,
				response);
	}
}
