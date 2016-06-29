package com.jb.forms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jb.dao.ServiceDao;
import com.jb.entities.Service;

public class CreationServiceForm {

	public static final String PARAM_SERVICETYPE = "servicetype";
	public static final String PARAM_ADDRESS = "address";

	public static final String ATT_SESSION_CUSTOMERS = "customerlist";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	private ServiceDao serviceDao;

	public CreationServiceForm(ServiceDao serviceDao) {
		this.serviceDao = serviceDao;
	}

	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public Service createService(HttpServletRequest request) {

		// String date = request.getParameter( "dateCommande" );
		String serviceTypeStr = getValeurChamp(request, PARAM_SERVICETYPE);
		String address = getValeurChamp(request, PARAM_ADDRESS);

		// Add try catch for service interpretation, search for pattern to build
		// abstract class with subclasses
		// try {
		// temp = Double.parseDouble(montant);
		// if (temp < 0) {
		// throw new FormValidationException("Le montant doit être un nombre
		// positif.");
		// }
		// } catch (NumberFormatException e) {
		// temp = -1;
		// throw new FormValidationException("Le montant doit être un nombre.");
		// }

		int serviceType = Integer.parseInt(serviceTypeStr);
		Service service = Service.createService(serviceType);

		service.setCreation(new Date());

		if (address == null)
			erreurs.put(PARAM_ADDRESS, "Merci de saisir une addresse.");
		else if (address.length() < 2)
			erreurs.put(PARAM_ADDRESS, "L'address doit contenir au moins 2 caractères.");
		service.setAddress(address);

		if (erreurs.isEmpty()) {
			resultat = "Succès de la création du service.";
			serviceDao.create(service);
		} else
			resultat = "Échec de la création du service.";

		return service;
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
