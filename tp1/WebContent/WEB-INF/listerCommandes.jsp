<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Lister commandes</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
    	<c:import url="/inc/menu.jsp" />
    	<c:if test="${empty sessionScope.customerlist}">
    		<p class="info">Aucune commande enregistré</p>
    	</c:if>
    	<c:if test="${!empty sessionScope.customerlist}">
	        <div>
	        <table>
	        <tr><th>Client</th><th>Date</th><th>Montant</th><th>Mode Paiement</th><th>Statut de paiement</th><th>Mode de livraison</th><th>Statut de livraison</th><th>Action</th></tr>
	        <c:forEach items="${sessionScope.orderlist}" var="item">
		        <tr>
		        <td><c:out value="${item.value.customer.firstName}"/><c:out value="${item.value.customer.lastName}"/></td>
		        <td><c:out value="${item.value.date}"/></td>
		        <td><c:out value="${item.value.amount}"/></td>
		        <td><c:out value="${item.value.paymentMethod}"/></td>
		        <td><c:out value="${item.value.paymentStatus}"/></td>
		        <td><c:out value="${item.value.deliveryMode}"/></td>
		        <td><c:out value="${item.value.deliveryStatus}"/></td>
		        <td><form action="" method="post">
    				<button name="order" value="${item.value.date.time}">X</button>
					</form>
				</td>
		     	</tr>
	        </c:forEach>
	        </table>
	        </div>
	    </c:if>
    </body>
</html>