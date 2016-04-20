<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'un client</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
    	<c:import url="/inc/menu.jsp" />
        <div>
            <form method="post" action="<c:url value="/creationClient"/>">
            	<fieldset>
            		<legend>Informations client</legend>
                	<c:import url="/inc/clientInput.jsp" />
					<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
				</fieldset>

                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
            </form>
        </div>
    </body>
</html>