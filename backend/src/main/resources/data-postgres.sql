insert into address (city, number, state, street) values ('Citty', 'Numbeer', 'Sstate', 'Street');
insert into address (city, number, state, street) values ('Citty2', 'Numbeer2', 'Sstate2', 'Street2');
insert into address (city, number, state, street) values ('Citty3', 'Numbeer3', 'Sstate3', 'Street3');

--patients
insert into users (address_id, email, name, password, phone_number, role, surname) values (1, 'nesto@nesto.com', 'A', 'password', '012456789', 0, 'S');
insert into patient (points, id) values (10, 1);
insert into users (address_id, email, name, password, phone_number, role, surname) values (2, 'nesto4@nesto.com', 'C', 'password', '012456789', 0, 'T');
insert into patient (points, id) values (1, 2);
insert into users (address_id, email, name, password, phone_number, role, surname) values (1, 'nesto5@nesto.com', 'B', 'password', '012456789', 0, 'Q');
insert into patient (points, id) values (12, 3);
insert into users (address_id, email, name, password, phone_number, role, surname) values (1, 'nesto6@nesto.com', 'D', 'password', '012456789', 0, 'R');
insert into patient (points, id) values (10, 4);
insert into users (address_id, email, name, password, phone_number, role, surname) values (1, 'nesto7@nesto.com', 'F', 'password', '012456789', 0, 'P');
insert into patient (points, id) values (1, 5);
insert into users (address_id, email, name, password, phone_number, role, surname) values (1, 'nesto8@nesto.com', 'E', 'password', '012456789', 0, 'U');
insert into patient (points, id) values (12, 6);

--dermatologists
insert into users (address_id, email, name, password, phone_number, role, surname) values (2, 'nesto2@nesto.com', 'Derma', 'password', '012456789', 1, 'Dermic');
insert into doctor (id) values (7);
insert into dermatologist (id) values (7);

--pharmacists
insert into users (address_id, email, name, password, phone_number, role, surname) values (2, 'nesto3@nesto.com', 'Pharma', 'password', '012456789', 2, 'Pharmic');
insert into doctor (id) values (8);
insert into pharmacist (id) values (8);

--medicine
insert into medicine (code, manufacturer, name, note, perscription, points, shape, structure, type) values ('AZ45', 'Nemci', 'PHIIIZER', 'vakcina', 0, 3, 'PILL', 'struucturee', 'some type idl');
insert into medicine (code, manufacturer, name, note, perscription, points, shape, structure, type) values ('BZ55', 'Kinezi', 'Sinopharm', 'vakcina', 0, 5, 'PILL', 'struucturee', 'some type idl');

--pharmacies
insert into pharmacy (description, name, address_id) values ('Pharm Desc', 'Nooice Pharmacy', 2);
insert into pharmacy (description, name, address_id) values ('GOod Pharm Desc', 'Veery Nooice Pharmacy', 3);

--works
insert into work (doctor_id, end_hour, pharmacy_id, start_hour) values (7, CURRENT_TIME, 1, CURRENT_TIME);
insert into pharmacy_staff (pharmacy_id, staff_id) values (1, 1);
insert into work (doctor_id, end_hour, pharmacy_id, start_hour) values (8, CURRENT_TIME, 1, CURRENT_TIME);
insert into pharmacy_staff (pharmacy_id, staff_id) values (1, 2);
insert into work (doctor_id, end_hour, pharmacy_id, start_hour) values (7, CURRENT_TIME, 2, CURRENT_TIME);
insert into pharmacy_staff (pharmacy_id, staff_id) values (2, 3);

--med price
insert into med_price(price) values (200);
insert into med_price(price) values (100);

--med catalog in pharmacy
insert into medicine_in_pharmacy (in_stock, current_price_id, medicine_id, pharmacy_id) values (5000, 1,1,1);
update med_price SET medicine_in_pharmacy_id = 1 WHERE id = 1;
insert into pharmacy_medicines (pharmacy_id, medicines_id) values (1,1);
--
insert into medicine_in_pharmacy (in_stock, current_price_id, medicine_id, pharmacy_id) values (10000, 2,2,1);
update med_price SET medicine_in_pharmacy_id = 2 WHERE id = 2;
insert into pharmacy_medicines (pharmacy_id, medicines_id) values (1,2);

--appointments
-- id 1
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (7, 30, null, 1, 200, current_timestamp + INTERVAL '3day', 0);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (7, 30, null, 1, 300, current_timestamp, 0);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (7, 30, null, 1, 400, current_timestamp, 0);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (7, 30, null, 1, 500, current_timestamp, 0);
insert into examination (diagnose, examination_info, status, appointment_id, patient_id) values ('Diagnose', 'Exam infoo NOTHELD', 0, 2, 1);
insert into examination (diagnose, examination_info, status, appointment_id, patient_id) values ('Diagnose', 'Exam infoo NOTHELD', 1, 3, 1);
insert into examination (diagnose, examination_info, status, appointment_id, patient_id) values ('Diagnose', 'Exam infoo NOTHELD', 2, 4, 1);
update appointment SET examination_id = 1 WHERE id = 2;
update appointment SET examination_id = 2 WHERE id = 3;
update appointment SET examination_id = 3 WHERE id = 4;
update appointment SET examination_id = 4 WHERE id = 5;


-- insert into patient_my_examinations (patient_id, my_examinations_id) values (1, 1);
-- insert into patient_my_examinations (patient_id, my_examinations_id) values (1, 2);
-- insert into patient_my_examinations (patient_id, my_examinations_id) values (1, 3);


-- --Therapy
-- insert into therapy_item (days, medicine_id) values (5, 1);
-- insert into therapy_item (days, medicine_id) values (5, 2);
--
-- insert into examination_therapy (examination_id, therapy_id) values (1,1);
-- insert into examination_therapy (examination_id, therapy_id) values (1,2);


-- Penalities
insert into penality (date, reason) values (current_timestamp, 'reason 1');
insert into patient_penalties (patient_id, penalties_id) values (1,1);
-- insert into penality (date, reason) values (current_timestamp, 'reason 2');
-- insert into penality (date, reason) values (current_timestamp, 'reason 3');
-- insert into patient_penalties (patient_id, penalties_id) values (1,2);
-- insert into patient_penalties (patient_id, penalties_id) values (1,3);

-- Subscriptions
insert into patient_subscriptions (patient_id, subscriptions_id) values (1,1);
insert into patient_subscriptions (patient_id, subscriptions_id) values (1,2);
