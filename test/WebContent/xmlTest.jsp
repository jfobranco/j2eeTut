<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Test</title>
	</head>
	<body>
		<c:import url="monDocument.xml" varReader="monReader">
 			<%-- Parse le contenu du fichier XML monDocument.xml dans une variable nommée 'doc' --%>
			<x:parse var="doc" doc="${monReader}" />
			<x:out select="$doc/news/article/auteur" /> <br>
			<x:out select="count($doc/news/article/auteur)" />

			<%-- Enregistre le résultat de l'expression XPath, spécifiée dans l'attribut select, 
			dans une variable de session nommée 'auteur' --%>
			<x:set var="auteur" scope="session" select="$doc//auteur" />

			<%-- Affiche le contenu de la variable nommée 'auteur' enregistrée en session --%>
			<x:out select="$sessionScope:auteur" />

			<%-- Afficher le titre de la news postée par 'Paul' --%>
			<x:if select="$doc/news/article[auteur='Paul']">
			   Paul a déjà posté une news dont voici le titre :
			   <x:out select="$doc/news/article[auteur='Paul']/titre" />
			</x:if>

			<%-- Affiche le titre de la news postée par 'Nicolas' si elle existe, et un simple message sinon --%>
			<x:choose>
			   <x:when select="$doc/news/article[auteur='Nicolas']">
			      Nicolas a déjà posté une news dont voici le titre :
			      <x:out select="$doc/news/article[auteur='Nicolas']/titre" />
			   </x:when>
			   <x:otherwise>
			      Nicolas n'a pas posté de news.
			   </x:otherwise>
			</x:choose>

			<!-- Affiche les auteurs et titres de tous les articles -->
			<p>
			<x:forEach var="element" select="$doc/news/article">
			   <strong><x:out select="$element/auteur" /></strong> :
			   <x:out select="$element/titre" />.<br/>
			</x:forEach>
			</p>
		</c:import>
	</body>
</html>