package com.jb.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jb.dao.UserDao;
import com.jb.dao.DAOException;
import com.jb.entities.Customer;

public class CreationClientForm {
	public static final String PARAM_FIRSTNAME = "prenomClient";
	public static final String PARAM_NAME = "nomClient";
	public static final String PARAM_ADDRESS = "adresseClient";
	public static final String PARAM_PHONE = "telephoneClient";
	public static final String PARAM_MAIL = "emailClient";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	private UserDao customerDao;

	public CreationClientForm(UserDao customerDao) {
		this.customerDao = customerDao;
	}

	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public Customer createCustomer(HttpServletRequest request) {
		String firstName = getValeurChamp(request, PARAM_FIRSTNAME);
		String lastName = getValeurChamp(request, PARAM_NAME);
		String address = getValeurChamp(request, PARAM_ADDRESS);
		String phone = getValeurChamp(request, PARAM_PHONE);
		String mail = getValeurChamp(request, PARAM_MAIL);

		/* Création du bean */
		Customer customer = new Customer();

		/* Initialisation de ses propriétés */
		if (lastName == null)
			erreurs.put(PARAM_NAME, "Merci de saisir un nom.");
		else if (lastName.length() < 2)
			erreurs.put(PARAM_NAME, "Le nom doit contenir au moins 3 caractères.");
		customer.setLastName(lastName);

		if (firstName != null && firstName.length() < 2)
			erreurs.put(PARAM_FIRSTNAME, "Le nom doit contenir au moins 3 caractères.");
		customer.setFirstName(firstName);

		if (address == null)
			erreurs.put(PARAM_ADDRESS, "Merci de saisir une addresse.");
		else if (address.length() < 10)
			erreurs.put(PARAM_ADDRESS, "L'addresse doit contenir au moins 10 caractères.");
		customer.setAddress(address);

		if (phone == null)
			erreurs.put(PARAM_PHONE, "Merci de saisir un téléphone.");
		else {
			if (phone.length() < 4)
				erreurs.put(PARAM_PHONE, "Le telephone doit contenir au moins 4 chiffres.");
			try {
				int phoneInt = Integer.valueOf(phone);
				if (phoneInt < 0)
					erreurs.put(PARAM_PHONE, "Le telephone doit être un chiffre positif.");
			} catch (NumberFormatException e) {
				erreurs.put(PARAM_PHONE, "Le telephone doit être un chiffre.");
			}
		}
		customer.setPhone(phone);

		if (mail != null && !mail.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)"))
			erreurs.put(PARAM_MAIL, "Merci de saisir une adresse mail valide.");
		customer.setMail(mail);

		if (erreurs.isEmpty()) {
			resultat = "Succès de la création du client.";
			try {
				customerDao.create(customer);
			} catch (DAOException e) {
				resultat = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
				e.printStackTrace();
			}
		} else
			resultat = "Échec de la création du client.";

		return customer;
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
