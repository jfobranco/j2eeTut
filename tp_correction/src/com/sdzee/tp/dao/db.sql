CREATE DATABASE tp_sdzee DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
GRANT ALL ON tp_sdzee.* TO 'java'@'localhost' IDENTIFIED BY '$dZ_EE';

CREATE TABLE tp_sdzee.Client (
 id INT( 11 ) NOT NULL AUTO_INCREMENT ,
 nom VARCHAR( 20 ) NOT NULL ,
 prenom VARCHAR( 20 ) ,
 adresse VARCHAR( 200 ) NOT NULL ,
 telephone VARCHAR( 10 ) NOT NULL,
 email VARCHAR( 60 ) ,
 image VARCHAR( 200 ) ,
 PRIMARY KEY ( id )
) ENGINE = INNODB;


CREATE TABLE tp_sdzee.Commande (
 id INT( 11 ) NOT NULL AUTO_INCREMENT ,
 id_client INT( 11 ) ,
 date DATETIME NOT NULL ,
 montant DEC( 11 ) NOT NULL ,
 mode_paiement VARCHAR( 20 ) NOT NULL ,
 statut_paiement VARCHAR( 20 ) ,
 mode_livraison VARCHAR( 20 ) NOT NULL ,
 statut_livraison VARCHAR( 20 ) ,
 PRIMARY KEY ( id ) ,
 CONSTRAINT fk_id_client      -- On donne un nom à notre clé
    FOREIGN KEY (id_client)   -- Colonne sur laquelle on crée la clé
    REFERENCES Client(id)     -- Colonne de référence (celle de la table Client)
    ON DELETE SET NULL        -- Action à effectuer lors de la suppression d'une référence
) ENGINE = INNODB;