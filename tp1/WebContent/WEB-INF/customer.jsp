<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Montrer client</title>
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
        </div>
    </body>
</html>