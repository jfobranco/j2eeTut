<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Montrer commande</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
    	<c:import url="/inc/menu.jsp" />
    	<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
        <div>
        Client<br>
        Prénom: <c:out value="${order.customer.firstName}"/><br>
        Nom: <c:out value="${order.customer.lastName}"/><br>
        Address: <c:out value="${order.customer.address}"/><br>
        Téléphone: <c:out value="${order.customer.phone}"/><br>
        Mail: <c:out value="${order.customer.mail}"/><br>
		<br>
		Commande<br>
        Date: <c:out value="${order.date}"/><br>
        Montant: <c:out value="${order.amount}"/><br>
        Mode paiement: <c:out value="${order.paymentMethod}"/><br>
        Statut paiement: <c:out value="${order.paymentStatus}"/><br>
        Mode livraison: <c:out value="${order.deliveryMode}"/><br>
        Status livraison: <c:out value="${order.deliveryStatus}"/><br>
        </div>
    </body>
</html>