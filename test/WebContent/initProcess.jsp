<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Montrer reponse</title>
    </head>
    <body>
    	Show all parameters
    	<c:forEach var="parametre" items="${ paramValues }"> 
			<ul>
				<li><b><c:out value="${ parametre.key }"/></b> :</li>
				<c:forEach var="value" items="${ parametre.value }">
					<c:out value="${ value }"/>   
				</c:forEach>
			</ul>
		</c:forEach>
    	<c:out value="${param.nom}" /> <c:out value="${param.prenom}" /><br>
	  <br>
	  <c:choose>
			<c:when test="${ !empty paramValues.pays }">
		    	<c:forEach items="${paramValue.pays}" var="pays">
		    		<c:out value="${pays}" /><br>
		    	</c:forEach>
		    	<c:out value="${param.autre}" /><br>
	        </c:when>
			<c:otherwise>
				Vous n'avez pas visité de pays parmi la liste proposée.<br/>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${ !empty param.autre }">
			<c:forTokens var="pays" items="${ param.autre }" delims=","> 
				<c:out value="${ pays }"/><br/>
			</c:forTokens>
			</c:when>
			<c:otherwise>
				Vous n'avez pas visité d'autre pays.<br/>
			</c:otherwise>
		</c:choose>
    </body>
</html>