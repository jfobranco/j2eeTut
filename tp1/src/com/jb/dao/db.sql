Password CHANGED!






CREATE DATABASE j2eeTP DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
GRANT ALL ON j2eeTP.* TO 'java'@'localhost' IDENTIFIED BY 'j2eeTP';

CREATE TABLE  j2eeTP.Customer (
 id INT( 11 ) NOT NULL AUTO_INCREMENT,
 firstName VARCHAR( 20 ),
 lastName VARCHAR( 20 ) NOT NULL,
 address VARCHAR( 255 ) NOT NULL,
 phone VARCHAR( 15 ) NOT NULL,
 mail VARCHAR( 60 ),
 PRIMARY KEY ( id )
) ENGINE = INNODB;

CREATE TABLE  j2eeTP.Order (
 id INT( 11 ) NOT NULL AUTO_INCREMENT,
 clientId INT( 11 ) NOT NULL,
 date DATETIME NOT NULL,
 paymentMethod VARCHAR( 20 ) NOT NULL,
 paymentStatus VARCHAR( 20 ) NOT NULL,
 deliveryMode VARCHAR( 20 ) NOT NULL,
 deliveryStatus VARCHAR( 20 ) NOT NULL,
 PRIMARY KEY ( id )
) ENGINE = INNODB;