<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Montrer client</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
    	<c:import url="/inc/menu.jsp" />
    	<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
        <div>
        Prénom: <c:out value="${customer.firstName}"/><br><br>
        Nom: <c:out value="${customer.lastName}"/><br><br>
        Address: <c:out value="${customer.address}"/><br><br>
        Téléphone:<c:out value="${customer.phone}"/><br><br>
        Mail: <c:out value="${customer.mail}"/>
        </div>
    </body>
</html>