<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Lister clients</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
    	<c:import url="/inc/menu.jsp" />
    	<c:if test="${empty sessionScope.customerlist}">
    		<p class="info">Aucun client enregistré</p>
    	</c:if>
    	<c:if test="${!empty sessionScope.customerlist}">
	        <div>
	        <table>
	        <tr><th>Prénom</th><th>Nom</th><th>Address</th><th>Téléphone</th><th>Mail</th><th>Action</th></tr>
	        <c:forEach items="${sessionScope.customerlist}" var="item">
		        <tr>
		        <td><c:out value="${item.value.firstName}"/></td>
		        <td><c:out value="${item.value.lastName}"/></td>
		        <td><c:out value="${item.value.address}"/></td>
		        <td><c:out value="${item.value.phone}"/></td>
		        <td><c:out value="${item.value.mail}"/></td>
		        <td><form action="" method="post">
    				<button name="customer" value="${item.value.lastName}">X</button>
					</form>
				</td>
		     	</tr>
	        </c:forEach>
	        </table>
	        </div>
	    </c:if>
    </body>
</html>