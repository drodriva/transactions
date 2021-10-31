INSERT INTO customer (customer_name, address1, address2, email, phone, country, country_code, post_code, region, city) VALUES('drodriva', 'address1', 'street', 'hi@hi.com', '074587452', 'Great Britain', 'UK', 'EEE BBB', 'London', 'London');
INSERT INTO customer (customer_name, address1, address2, email, phone, country, country_code, post_code, region, city) VALUES('drodriva2', 'address2', 'street2', 'hi2@hi2.com', '0478547852', 'Poland', 'PO', 'EEE BBB', 'Poznan', 'Poznan');
INSERT INTO customer (customer_name, address1, address2, email, phone, country, country_code, post_code, region, city)  VALUES('drodriva3', 'address3', 'street3', 'hi3@hi3.com', '965874123', 'Spain', 'ES', 'WWW ZZZ', 'Madrid', 'Madrid');


INSERT INTO account (description, balance, currency_code, creation_date, customer_id) VALUES('number1', 1.0, 'EUR', {ts '2013-10-17 18:47:52.69'}, 3);
INSERT INTO account (description, balance, currency_code, creation_date, customer_id) VALUES('number2', 1.0, 'EUR', {ts '2014-09-20 13:47:52.69'}, 2);
INSERT INTO account (description, balance, currency_code, creation_date, customer_id) VALUES('number3', 1.0, 'EUR', {ts '2015-05-17 18:47:52.69'}, 1);
INSERT INTO account (description, balance, currency_code, creation_date, customer_id) VALUES('number4', 1.0 ,'GBP', {ts '2016-09-09 14:47:52.69'}, 1);

INSERT INTO transaction (amount, date, balance, concept, transaction_type, account_id) VALUES(1.12, {ts '2012-09-17 18:47:52.69'}, 55.0, 'concept1', 0 ,1);
INSERT INTO transaction (amount, date, balance, concept, transaction_type, account_id) VALUES(3.0, {ts '2012-09-17 18:47:52.69'}, 12.1, 'concept2', 1, 3);
INSERT INTO transaction (amount, date, balance, concept, transaction_type, account_id) VALUES(144.0, {ts '2012-09-17 18:47:52.69'}, 369.21, 'concept3', 0,2);
INSERT INTO transaction (amount, date, balance, concept, transaction_type, account_id) VALUES(56.74, {ts '2012-09-17 18:47:52.69'}, 89, 'concept4', 1,4);
INSERT INTO transaction (amount, date, balance, concept, transaction_type, account_id) VALUES(12.0, {ts '2012-09-17 18:47:52.69'}, 74.33, 'concept5', 0,1);
INSERT INTO transaction (amount, date, balance, concept, transaction_type, account_id) VALUES(1.0, {ts '2012-09-17 18:47:52.69'}, 96.2, 'concept6', 1,3);

INSERT INTO transaction (amount, date, balance, concept, transaction_type, account_id) VALUES(2.0, {ts '2013-09-17 18:47:52.69'}, 55, 'concept7', 0,1);
INSERT INTO transaction (amount, date, balance, concept, transaction_type, account_id) VALUES(5.0, {ts '2015-09-17 18:47:52.69'}, 0, 'concept8', 1 ,1);
INSERT INTO transaction (amount, date, balance, concept, transaction_type, account_id) VALUES(45345.0, {ts '2017-09-17 18:47:52.69'}, 2.0, 'concept9', 0,2);
INSERT INTO transaction (amount, date, balance, concept, transaction_type, account_id) VALUES(345.0, {ts '2020-09-17 18:47:52.69'}, 36.0, 'concept10', 1 ,1);
INSERT INTO transaction (amount, date, balance, concept, transaction_type, account_id) VALUES(23.0, {ts '2021-09-17 18:47:52.69'}, 699.3, 'concept11', 0,1);
INSERT INTO transaction (amount, date, balance, concept, transaction_type, account_id) VALUES(0.0, {ts '2021-09-17 18:47:52.69'}, 852.01, 'concept12',  1,1);