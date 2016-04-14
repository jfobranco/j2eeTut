package com.jb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jb.beans.Order;
import com.jb.forms.CreationCommandeForm;

public class CreationCommande extends HttpServlet {

	public static final String ATT_FORM = "form";
	public static final String ATT_ORDER = "order";

	public static final String VIEW = "/WEB-INF/creationCommande.jsp";
	public static final String VIEW2 = "/WEB-INF/afficherCommande.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Affichage de la page d'inscription */
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Création et initialisation du message. */
		CreationCommandeForm form = new CreationCommandeForm();

		Order order = form.createOrder(request);
		/* Stockage du message et du bean dans l'objet request */
		request.setAttribute(ATT_ORDER, order);
		request.setAttribute(ATT_FORM, form);

		/* Transmission de la paire d'objets request/response à notre JSP */
		this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
	}
}
