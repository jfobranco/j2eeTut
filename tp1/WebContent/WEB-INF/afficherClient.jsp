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
    	${message}
    	<c:if test="${ !error }">
	        <div>
	        Prénom: <c:out value="${customer.firstName}"/><br>
	        Nom: <c:out value="${customer.lastName}"/><br>
	        Address: <c:out value="${customer.address}"/><br>
	        Téléphone: <c:out value="${customer.phone}"/><br>
	        Mail: <c:out value="${customer.mail}"/><br>
	        </div>
        </c:if>
    </body>
</html>