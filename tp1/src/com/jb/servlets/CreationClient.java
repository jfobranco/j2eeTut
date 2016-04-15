package com.jb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jb.beans.Customer;
import com.jb.forms.CreationClientForm;

public class CreationClient extends HttpServlet {

	public static final String ATT_CUSTOMER = "customer";
	public static final String ATT_FORM = "form";

	public static final String VIEW = "/WEB-INF/creationClient.jsp";
	public static final String VIEW2 = "/WEB-INF/afficherClient.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Affichage de la page d'inscription */
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CreationClientForm form = new CreationClientForm();

		Customer customer = form.createCustomer(request);

		request.setAttribute(ATT_FORM, form);
		request.setAttribute(ATT_CUSTOMER, customer);

		/* Transmission de la paire d'objets request/response à notre JSP */
		this.getServletContext().getRequestDispatcher(form.getErreurs().isEmpty() ? VIEW2 : VIEW).forward(request,
				response);
	}
}
