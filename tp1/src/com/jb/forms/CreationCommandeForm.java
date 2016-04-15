package com.jb.forms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jb.beans.Customer;
import com.jb.beans.Order;

public class CreationCommandeForm {

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

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public Order createOrder(HttpServletRequest request) {

		CreationClientForm clientForm = new CreationClientForm();
		Customer customer = clientForm.createCustomer(request);

		erreurs = clientForm.getErreurs();

		// String date = request.getParameter( "dateCommande" );
		String amount = getValeurChamp(request, PARAM_AMOUNT);
		String paymentMethod = getValeurChamp(request, PARAM_PAYMENTMODE);
		String paymentStatus = getValeurChamp(request, PARAM_PAYMENTSTATUS);
		String deliveryMode = getValeurChamp(request, PARAM_DELIVERYMODE);
		String deliveryStatus = getValeurChamp(request, PARAM_DELIVERYSTATUS);

		Order order = new Order();
		order.setCustomer(customer);
		order.setDate(new Date());

		Double amountDouble = null;
		if (amount == null)
			erreurs.put(PARAM_AMOUNT, "Merci de saisir un montant.");
		else {
			try {
				amountDouble = Double.parseDouble(amount);
			} catch (NumberFormatException e) {
				erreurs.put(PARAM_AMOUNT, "Le montant doit être un chiffre.");
			}
			if (amountDouble != null && amountDouble < 0)
				erreurs.put(PARAM_AMOUNT, "Le montant doit être un chiffre positif.");
		}
		order.setAmount(amountDouble);
		if (paymentMethod == null)
			erreurs.put(PARAM_PAYMENTMODE, "Merci de saisir une méthode de paiement.");
		else if (paymentMethod.length() < 2)
			erreurs.put(PARAM_PAYMENTMODE, "La méthode de paiement doit contenir au moins 2 caractères.");
		order.setPaymentMethod(paymentMethod);
		if (paymentStatus != null && paymentStatus.length() < 2)
			erreurs.put(PARAM_PAYMENTSTATUS, "Le status de payment doit contenir au moins 2 caractères.");
		order.setPaymentStatus(paymentStatus);
		if (deliveryMode == null)
			erreurs.put(PARAM_DELIVERYMODE, "Merci de saisir un mode de livraison.");
		else if (deliveryMode.length() < 2)
			erreurs.put(PARAM_DELIVERYMODE, "Le mode de livraison doit contenir au moins 2 caractères.");
		order.setDeliveryMode(deliveryMode);
		if (deliveryStatus != null && deliveryStatus.length() < 2)
			erreurs.put(PARAM_DELIVERYSTATUS, "Le status de livraison doit contenir au moins 2 caractères.");
		order.setDeliveryStatus(deliveryStatus);

		if (erreurs.isEmpty())
			resultat = "Succès de la création de la commande.";
		else
			resultat = "Échec de la création de la commande.";

		return order;
	}

	/*
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	 * sinon.
	 */
	private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur.trim();
		}
	}
}
