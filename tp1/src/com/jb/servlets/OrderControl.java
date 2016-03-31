package com.jb.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jb.beans.Customer;
import com.jb.beans.Order;

public class OrderControl extends HttpServlet {
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		 /* Création et initialisation du message. */
	    String firstName = request.getParameter( "prenomClient" );
	    String lastName = request.getParameter( "nomClient" );
	    String address = request.getParameter( "adresseClient" );
	    String phone = request.getParameter( "telephoneClient" );
	    String mail = request.getParameter( "emailClient" );

//	    String date = request.getParameter( "dateCommande" );
	    Double amount = Double.valueOf(request.getParameter( "montantCommande" ));
	    String paymentMethod = request.getParameter( "modePaiementCommande" );
	    String paymentStatus = request.getParameter( "statutPaiementCommande" );
	    String deliveryMode = request.getParameter( "modeLivraisonCommande" );
	    String deliveryStatus = request.getParameter( "statutLivraisonCommande" );

	    /* Création du bean */
	    Customer customer = new Customer();
	    Order order = new Order();
	    String message = "";

	    /* Initialisation de ses propriétés */
	    customer.setLastName(lastName);
	    customer.setFirstName(firstName);
	    customer.setAddress(address);
	    customer.setPhone(phone);
	    customer.setMail(mail);
	    if (lastName == "" || firstName == "" || address == "" || phone == "" || mail == "")
	    	message = "Missing fields in client";

	    order.setCustomer(customer);
	    order.setDate(new Date());
	    order.setAmount(amount);
	    order.setPaymentMethod(paymentMethod);
	    order.setPaymentStatus(paymentStatus);
	    order.setDeliveryMode(deliveryMode);
	    order.setDeliveryStatus(deliveryStatus);

	    if (amount == null || paymentMethod == "" || paymentStatus == "" || deliveryMode == "" || deliveryStatus == "")
	    	message = message != null ? message + ", " : "Missing fields in order";

	    if (message == null)
	    	message = "Commande creé avec succés";
	    else
	    	message += " <a href=\"creationClient.jsp\">Cliquez ici</a> pour accéder au formulaire de création d'un client.";

	    /* Stockage du message et du bean dans l'objet request */
	    request.setAttribute( "customer", customer );
	    request.setAttribute( "order", order );
	    request.setAttribute( "message", message );

	    /* Transmission de la paire d'objets request/response à notre JSP */
	    this.getServletContext().getRequestDispatcher( "/WEB-INF/order.jsp" ).forward( request, response );
	}
}
