<!-- Sélection du nœud racine -->
/

<!-- Sélection des nœuds 'article' enfants des nœuds 'news' -->
/news/article

<!-- Sélection de tous les nœuds inclus dans les nœuds 'article' enfants des nœuds 'news' -->
/news/article/*

<!-- Sélection de tous les nœuds 'auteur' qui ont deux parents quelconques -->
/*/*/auteur

<!-- Sélection de tous les nœuds 'auteur' du document via l'opérateur '//' -->
//auteur

<!-- Sélection de tous les nœuds 'article' ayant au moins un parent -->
/*//article

<!-- Sélection de l'attribut 'id' des nœuds 'article' enfants de 'news' -->
/news/article/@id

<!-- Sélection des nœuds 'article' enfants de 'news' dont la valeur du nœud 'auteur' est 'Paul' -->
/news/article[auteur='Paul']

<!-- Sélection des nœuds 'article' enfants de 'news' dont l'attribut id vaut '12' -->
/news/article[@id='12']