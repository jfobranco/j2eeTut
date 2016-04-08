<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Montrer commande</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
    	${message}
        <div>
        Prénom: ${customer.firstName}<br>
        Nom: ${customer.lastName}<br>
        Address: ${customer.address}<br>
        Téléphone: ${customer.phone}<br>
        Mail: ${customer.mail}<br>
		<br>
        Date: ${order.date}<br>
        Montant: ${order.amount}<br>
        Mode paiement: ${order.paymentMethod}<br>
        Statut paiement: ${order.paymentStatus}<br>
        Mode livraison: ${order.deliveryMode}<br>
        Status livraison: ${order.deliveryStatus}<br>
        </div>
    </body>
</html>