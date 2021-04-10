insert into address (id, city, number, state, street) values (1, 'Citty', 'Numbeer', 'Sstate', 'Street');
insert into address (id, city, number, state, street) values (2, 'Citty2', 'Numbeer2', 'Sstate2', 'Street2');
insert into address (id, city, number, state, street) values (3, 'Citty3', 'Numbeer3', 'Sstate3', 'Street3');

--patients
insert into users (id, address_id, email, name, password, phone_number, role, surname) values (1, 1, 'nesto@nesto.com', 'A', 'password', '012456789', 0, 'S');
insert into patient (points, id) values (10, 1);
insert into users (id, address_id, email, name, password, phone_number, role, surname) values (4, 1, 'nesto4@nesto.com', 'C', 'password', '012456789', 0, 'T');
insert into patient (points, id) values (1, 4);
insert into users (id, address_id, email, name, password, phone_number, role, surname) values (5, 1, 'nesto5@nesto.com', 'B', 'password', '012456789', 0, 'Q');
insert into patient (points, id) values (12, 5);
insert into users (id, address_id, email, name, password, phone_number, role, surname) values (6, 1, 'nesto6@nesto.com', 'D', 'password', '012456789', 0, 'R');
insert into patient (points, id) values (10, 6);
insert into users (id, address_id, email, name, password, phone_number, role, surname) values (7, 1, 'nesto7@nesto.com', 'F', 'password', '012456789', 0, 'P');
insert into patient (points, id) values (1, 7);
insert into users (id, address_id, email, name, password, phone_number, role, surname) values (8, 1, 'nesto8@nesto.com', 'E', 'password', '012456789', 0, 'U');
insert into patient (points, id) values (12, 8);

--dermatologists
insert into users (id, address_id, email, name, password, phone_number, role, surname) values (2, 2, 'nesto2@nesto.com', 'Derma', 'password', '012456789', 1, 'Dermic');
insert into doctor (id) values (2);
insert into dermatologist (id) values (2);

--pharmacists
insert into users (id, address_id, email, name, password, phone_number, role, surname) values (3, 2, 'nesto3@nesto.com', 'Pharma', 'password', '012456789', 2, 'Pharmic');
insert into doctor (id) values (3);
insert into pharmacist (id) values (3);

--medicine
insert into medicine (id, code, manufacturer, name, note, perscription, points, shape, structure, type) values (1, 'AZ45', 'Nemci', 'PHIIIZER', 'vakcina', 0, 3, 'PILL', 'struucturee', 'some type idl');
insert into medicine (id, code, manufacturer, name, note, perscription, points, shape, structure, type) values (2, 'BZ55', 'Kinezi', 'Sinopharm', 'vakcina', 0, 5, 'PILL', 'struucturee', 'some type idl');

--pharmacies
insert into pharmacy (id, description, name, address_id) values (1, 'Pharm Desc', 'Nooice Pharmacy', 2);
insert into pharmacy (id, description, name, address_id) values (2, 'GOod Pharm Desc', 'Veery Nooice Pharmacy', 3);

--works
insert into work (doctor_id, end_hour, pharmacy_id, start_hour, id) values (2, CURRENT_TIME, 1, CURRENT_TIME, 1);
insert into pharmacy_staff (pharmacy_id, staff_id) values (1, 1);
insert into work (doctor_id, end_hour, pharmacy_id, start_hour, id) values (3, CURRENT_TIME, 1, CURRENT_TIME, 2);
insert into pharmacy_staff (pharmacy_id, staff_id) values (1, 2);

--med price
insert into med_price(id, price) values (1, 200);
insert into med_price(id, price) values (2, 100);

--med catalog in pharmacy
insert into medicine_in_pharmacy (id, in_stock, current_price_id, medicine_id, pharmacy_id) values (1, 5000, 1,1,1);
update med_price SET medicine_in_pharmacy_id = 1;
insert into pharmacy_medicines (pharmacy_id, medicines_id) values (1,1);
--
insert into medicine_in_pharmacy (id, in_stock, current_price_id, medicine_id, pharmacy_id) values (2, 10000, 2,2,1);
update med_price SET medicine_in_pharmacy_id = 2;
insert into pharmacy_medicines (pharmacy_id, medicines_id) values (1,2);

--appointments
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type, id) values (2, 30, null, 1, 200, current_timestamp, 0, 1);