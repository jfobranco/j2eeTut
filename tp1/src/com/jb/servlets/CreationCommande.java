package com.jb.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jb.beans.Customer;
import com.jb.beans.Order;

public class CreationCommande extends HttpServlet {

	public static final String ATT_CUSTOMER = "customer";
	public static final String ATT_ORDER = "order";
	public static final String ATT_MESSAGE = "message";
	public static final String ATT_ERROR = "error";

	public static final String PARAM_FIRSTNAME = "prenomClient";
	public static final String PARAM_NAME = "nomClient";
	public static final String PARAM_ADDRESS = "adresseClient";
	public static final String PARAM_PHONE = "telephoneClient";
	public static final String PARAM_MAIL = "emailClient";

	public static final String PARAM_AMOUNT = "montantCommande";
	public static final String PARAM_PAYMENTMODE = "modePaiementCommande";
	public static final String PARAM_PAYMENTSTATUS = "statutPaiementCommande";
	public static final String PARAM_DELIVERYMODE = "modeLivraisonCommande";
	public static final String PARAM_DELIVERYSTATUS = "statutLivraisonCommande";

	public static final String VIEW = "/WEB-INF/creationCommande.jsp";
	public static final String VIEW2 = "/WEB-INF/afficherCommande.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Affichage de la page d'inscription */
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Création et initialisation du message. */
		String firstName = request.getParameter(PARAM_FIRSTNAME);
		String lastName = request.getParameter(PARAM_NAME);
		String address = request.getParameter(PARAM_ADDRESS);
		String phone = request.getParameter(PARAM_PHONE);
		String mail = request.getParameter(PARAM_MAIL);

		// String date = request.getParameter( "dateCommande" );
		double amount;
		try {
			amount = Double.parseDouble(request.getParameter(PARAM_AMOUNT));
		} catch (NumberFormatException e) {
			amount = -1;
		}
		String paymentMethod = request.getParameter(PARAM_PAYMENTMODE);
		String paymentStatus = request.getParameter(PARAM_PAYMENTSTATUS);
		String deliveryMode = request.getParameter(PARAM_DELIVERYMODE);
		String deliveryStatus = request.getParameter(PARAM_DELIVERYSTATUS);

		/* Création du bean */
		Customer customer = new Customer();
		Order order = new Order();
		String message = null;
		boolean error = false;

		/* Initialisation de ses propriétés */
		customer.setLastName(lastName);
		customer.setFirstName(firstName);
		customer.setAddress(address);
		customer.setPhone(phone);
		customer.setMail(mail);
		if (lastName.trim().isEmpty() || address.trim().isEmpty() || phone.trim().isEmpty()) {
			message = "Missing fields in client";
			error = true;
		}

		order.setCustomer(customer);
		order.setDate(new Date());
		order.setAmount(amount);
		order.setPaymentMethod(paymentMethod);
		order.setPaymentStatus(paymentStatus);
		order.setDeliveryMode(deliveryMode);
		order.setDeliveryStatus(deliveryStatus);

		if (paymentMethod.isEmpty() || deliveryMode.isEmpty())
			message = message != null ? message + ", " : "Missing fields in order";

		if (message == null)
			message = "Commande creé avec succés";
		else
			message += " <a href=\"creationClient.jsp\">Cliquez ici</a> pour accéder au formulaire de création d'un client.";

		/* Stockage du message et du bean dans l'objet request */
		request.setAttribute(ATT_CUSTOMER, customer);
		request.setAttribute(ATT_ORDER, order);
		request.setAttribute(ATT_MESSAGE, message);
		request.setAttribute(ATT_ERROR, error);

		/* Transmission de la paire d'objets request/response à notre JSP */
		this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
	}
}
