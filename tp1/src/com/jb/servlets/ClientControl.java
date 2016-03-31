package com.jb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jb.beans.Customer;


public class ClientControl extends HttpServlet {
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		 /* Création et initialisation du message. */
	    String firstName = request.getParameter( "prenomClient" );
	    String lastName = request.getParameter( "nomClient" );
	    String address = request.getParameter( "adresseClient" );
	    String phone = request.getParameter( "telephoneClient" );
	    String mail = request.getParameter( "emailClient" );

	    /* Création du bean */
	    Customer customer = new Customer();
	    String message = null;

	    /* Initialisation de ses propriétés */
	    customer.setLastName(lastName);
	    customer.setFirstName(firstName);
	    customer.setAddress(address);
	    customer.setPhone(phone);
	    customer.setMail(mail);
	    
	    if (lastName == "" || firstName == "" || address == "" || phone == "" || mail == "")
	    	message = "Missing fields in client. <a href=\"creationClient.jsp\">Cliquez ici</a> pour accéder au formulaire de création d'un client.";
	    else
	    	message = "Client creé avec succés";


	    /* Stockage du message et du bean dans l'objet request */
	    request.setAttribute( "customer", customer );
	    request.setAttribute( "message", message );

	    /* Transmission de la paire d'objets request/response à notre JSP */
	    this.getServletContext().getRequestDispatcher( "/WEB-INF/customer.jsp" ).forward( request, response );
	}
}
