package com.jb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jb.beans.Customer;

public class CreationClient extends HttpServlet {

	public static final String ATT_CUSTOMER = "customer";
	public static final String ATT_MESSAGE = "message";
	public static final String ATT_ERROR = "error";

	public static final String PARAM_FIRSTNAME = "prenomClient";
	public static final String PARAM_NAME = "nomClient";
	public static final String PARAM_ADDRESS = "adresseClient";
	public static final String PARAM_PHONE = "telephoneClient";
	public static final String PARAM_MAIL = "emailClient";

	public static final String VIEW = "/afficherClient.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Création et initialisation du message. */
		String firstName = request.getParameter(PARAM_FIRSTNAME);
		String lastName = request.getParameter(PARAM_NAME);
		String address = request.getParameter(PARAM_ADDRESS);
		String phone = request.getParameter(PARAM_PHONE);
		String mail = request.getParameter(PARAM_MAIL);

		/* Création du bean */
		Customer customer = new Customer();
		String message = null;
		boolean error = false;

		/* Initialisation de ses propriétés */
		customer.setLastName(lastName);
		customer.setFirstName(firstName);
		customer.setAddress(address);
		customer.setPhone(phone);
		customer.setMail(mail);

		if (lastName.trim().isEmpty() || address.trim().isEmpty() || phone.trim().isEmpty()) {
			message = "Missing fields in client. <a href=\"creationClient.jsp\">Cliquez ici</a> pour accéder au formulaire de création d'un client.";
			error = true;
		} else
			message = "Client creé avec succés";

		/* Stockage du message et du bean dans l'objet request */
		request.setAttribute(ATT_CUSTOMER, customer);
		request.setAttribute(ATT_MESSAGE, message);
		request.setAttribute(ATT_ERROR, error);

		/* Transmission de la paire d'objets request/response à notre JSP */
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
