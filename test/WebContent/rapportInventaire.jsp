<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Test</title>
	</head>
	<body>
		<c:import url="xmlExercise.xml" varReader="monReader">
 			<x:parse var="doc" doc="${monReader}" />
			<x:forEach var="element" select="$doc/inventaire/livre">
			   <strong><x:out select="$element/auteur" /></strong> :
			   <x:out select="$element/titre" />.<br/>
			   <x:if select="$element[stock<minimum]">
			   		Low stock!<br/>
			   	</x:if>
			</x:forEach>
		</c:import>
	</body>
</html>