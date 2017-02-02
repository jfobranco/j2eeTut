INSERT INTO customer (ID, ADDRESS, FIRSTNAME, INSCRIPTIONDATE, LASTNAME, MAIL, PASSWORD, PHONE) VALUES (1, 'Address 1', 'Customer 1', NOW(), 'LastName 1', 'customer1@jb.com', '1234', '4321');
INSERT INTO customer (ID, ADDRESS, FIRSTNAME, INSCRIPTIONDATE, LASTNAME, MAIL, PASSWORD, PHONE) VALUES (2, 'Address 2', 'Customer 2', NOW(), 'LastName 2', 'customer2@jb.com', '1234', '4321');
INSERT INTO customer (ID, ADDRESS, FIRSTNAME, INSCRIPTIONDATE, LASTNAME, MAIL, PASSWORD, PHONE) VALUES (3, 'Address 3', 'Customer 3', NOW(), 'LastName 3', 'customer3@jb.com', '1234', '4321');

INSERT INTO Service (ID, ADDRESS, CREATION, MAIL, NAME, PHONE, ownerId) VALUES (1, 'Address 100', NOW(), 'service1@jb.com', 'Service 1', '5678', 1);
INSERT INTO Service (ID, ADDRESS, CREATION, MAIL, NAME, PHONE, ownerId) VALUES (2, 'Address 101', NOW(), 'service2@jb.com', 'Service 2', '5678', 2);
INSERT INTO Service (ID, ADDRESS, CREATION, MAIL, NAME, PHONE, ownerId) VALUES (3, 'Address 102', NOW(), 'service3@jb.com', 'Service 3', '5678', 3);

INSERT INTO Post (ID, TITLE, TEXT, DATE, service) VALUES (1, 'Test', 'Test 1', NOW(), 1);
INSERT INTO Post (ID, TITLE, TEXT, DATE, service) VALUES (2, 'Test2', 'Test 2', NOW(), 1);

INSERT INTO service_customer (customer_ID, service_ID) VALUES (1, 1);