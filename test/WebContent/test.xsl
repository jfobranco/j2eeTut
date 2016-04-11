<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
    <html xmlns="http://www.w3.org/1999/xhtml">
      <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Mise en forme avec XSLT</title>
      </head>
      <body>
        <table width="1000" border="1" cellspacing="0" cellpadding="0">
          <tr>
            <th scope="col">Id</th>
            <th scope="col">Auteur</th>
            <th scope="col">Titre</th>
            <th scope="col">Contenu</th>
          </tr>
          <xsl:for-each select="/news/article">
            <tr>
              <td>
                <xsl:value-of select="@id" />
              </td>
              <td>
                <xsl:value-of select="auteur" />
              </td>
              <td>
                <xsl:value-of select="titre" />
              </td>
              <td>
                <xsl:value-of select="contenu" />
              </td>
            </tr>
          </xsl:for-each>
        </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>