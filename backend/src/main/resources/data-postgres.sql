insert into address (city, number, state, street) values ('Beograd', '2', 'Serbia', 'Nemanjina');
insert into address (city, number, state, street) values ('Beograd', '33', 'Serbia', 'Laze Kostića');
insert into address (city, number, state, street) values ('Beograd', '21', 'Serbia', 'Brodarska');
insert into address (city, number, state, street) values ('Beograd', '11', 'Serbia', 'Vuka Karadžića');
insert into address (city, number, state, street) values ('Beograd', '2', 'Serbia', 'Magelanova');
insert into address (city, number, state, street) values ('Beograd', '26', 'Serbia', 'Partizanska');
insert into address (city, number, state, street) values ('Beograd', '17', 'Serbia', 'Kolarčeva');
insert into address (city, number, state, street) values ('Beograd', '9', 'Serbia', 'Aleksandra Fleminga');
insert into address (city, number, state, street) values ('Beograd', '31', 'Serbia', 'Bajronova');
insert into address (city, number, state, street) values ('Beograd', '49', 'Serbia', 'Ljutice Bogdana');
insert into address (city, number, state, street) values ('Beograd', '25', 'Serbia', 'Karađorđeva');
insert into address (city, number, state, street) values ('Beograd', '3', 'Serbia', 'Makedonska');
insert into address (city, number, state, street) values ('Beograd', '13', 'Serbia', 'Stevana Sremca');
insert into address (city, number, state, street) values ('Beograd', '13', 'Serbia', 'Varvarinska');
insert into address (city, number, state, street) values ('Beograd', '50', 'Serbia', 'Bežanijska');
insert into address (city, number, state, street) values ('Beograd', '14', 'Serbia', 'Plitvička');
insert into address (city, number, state, street) values ('Beograd', '9', 'Serbia', 'Ustanička');
insert into address (city, number, state, street) values ('Beograd', '29', 'Serbia', 'Bore Stankovića');
insert into address (city, number, state, street) values ('Beograd', '35', 'Serbia', 'Milunke Savić');
insert into address (city, number, state, street) values ('Beograd', '12', 'Serbia', 'Filipa Višnjića');
insert into address (city, number, state, street) values ('Beograd', '8', 'Serbia', 'Nikolaja Gogolja');
insert into address (city, number, state, street) values ('Beograd', '28', 'Serbia', 'Petra Kočića');
insert into address (city, number, state, street) values ('Beograd', '100', 'Serbia', 'Bulevar Arsenija Čarnojevića');
insert into address (city, number, state, street) values ('Beograd', '18', 'Serbia', 'Laze Lazarevića');
insert into address (city, number, state, street) values ('Beograd', '23', 'Serbia', 'Karlovačka');
insert into address (city, number, state, street) values ('Beograd', '90', 'Serbia', 'Lenjinov bulevar');
insert into address (city, number, state, street) values ('Beograd', '47', 'Serbia', 'Mačvanska');
insert into address (city, number, state, street) values ('Beograd', '30', 'Serbia', 'Terazije');
insert into address (city, number, state, street) values ('Beograd', '29', 'Serbia', 'Avalska');
insert into address (city, number, state, street) values ('Beograd', '41', 'Serbia', 'Milutina Milankovića');
insert into address (city, number, state, street) values ('Novi Sad', '128', 'Serbia', 'Bulevar oslobođenja');
insert into address (city, number, state, street) values ('Novi Sad', '43', 'Serbia', 'Bulevar oslobođenja');
insert into address (city, number, state, street) values ('Novi Sad', '8', 'Serbia', 'Trg slobode');
insert into address (city, number, state, street) values ('Novi Sad', '20', 'Serbia', 'Stražilovska');
insert into address (city, number, state, street) values ('Novi Sad', '23', 'Serbia', 'Vuka Karadžića');

--user roles
insert into user_roles (name) values ('SYS_ADMIN');
insert into user_roles (name) values ('PHARMACY_ADMIN');
insert into user_roles (name) values ('PATIENT');
insert into user_roles (name) values ('DERMATOLOGIST');
insert into user_roles (name) values ('PHARMACIST');
insert into user_roles (name) values ('SUPPLIER');

--IMPORTANT: when inserting new sample users, use https://www.bcryptcalculator.com/encode to correctly generate a password for database

--patients -- password: password
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (10, 'p1@maildrop.cc', true, 'Petar', '$2a$10$puaZa6SuasjiagmJJi6Dtecz7cxA3HuXJUzcqlana.SO.U22uXOJq', '0645597841', 3, 'Petrović', LOCALTIMESTAMP);
insert into patient (points, id) values (10, 1);
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (5, 'p2@maildrop.cc', true, 'Maja', '$2a$10$puaZa6SuasjiagmJJi6Dtecz7cxA3HuXJUzcqlana.SO.U22uXOJq', '0617794512', 3, 'Marković', LOCALTIMESTAMP);
insert into patient (points, id) values (1, 2);
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (7, 'p3@maildrop.cc', true, 'Živorad', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '0629745132', 3, 'Pantić', LOCALTIMESTAMP); -- TODO: odavde na dole je sifra svima 'password', osim sa coa i sis admina
insert into patient (points, id) values (12, 3);
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (13, 'p4@maildrop.cc', true, 'Zoran', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '0649985123', 3, 'Radovanović', LOCALTIMESTAMP);
insert into patient (points, id) values (10, 4);
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (19, 'natasha@maildrop.cc', true, 'Nataša', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '0635668741', 3, 'Bajunović', LOCALTIMESTAMP);
insert into patient (points, id) values (1, 5);
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (4, 'violet@maildrop.cc', true, 'Violeta', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '0629541238', 3, 'Milutinović', LOCALTIMESTAMP);
insert into patient (points, id) values (12, 6);
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (20, 'gidra@maildrop.cc', true, 'Dragan', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '0619745231', 3, 'Petrović', LOCALTIMESTAMP);
insert into patient (points, id) values (0, 7);
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (15, 'vesna123@maildrop.cc', true, 'Vesna', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '0615217941', 3, 'Tomašević', LOCALTIMESTAMP);
insert into patient (points, id) values (3, 8);
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (2, 'abcdefgh@maildrop.cc', true, 'Mirko', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '0619874511', 3, 'Ivanić', LOCALTIMESTAMP);
insert into patient (points, id) values (5, 9);
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (6, 'extramail@maildrop.cc', true, 'Žarko', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '0649874771', 3, 'Nikolić', LOCALTIMESTAMP);
insert into patient (points, id) values (0, 10);

--dermatologists
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (11, 'gordop@maildrop.cc', true, 'Gordan', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '0635521478', 4, 'Popović', LOCALTIMESTAMP);
insert into doctor (id) values (11);
insert into dermatologist (id) values (11);

--pharmacists
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (21, 'drmili@maildrop.cc', true, 'Milisav', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '0612223855', 5, 'Nikolić', LOCALTIMESTAMP);
insert into doctor (id) values (12);
insert into pharmacist (id) values (12);
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (22, 'nadamm28@maildrop.cc', true, 'Nada', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '0638895221', 5, 'Macura', LOCALTIMESTAMP);
insert into doctor (id) values (13);
insert into dermatologist (id) values (13);



--medicineSpecs
insert into medicine_specifications (side_effects, daily_intake, structure) values ('Pospanost, naleti raspoloženja','1 pilula na dan','metildopa');
insert into medicine_specifications (side_effects, daily_intake, structure) values ('Might learn mandarin','2 doses','Structurrrre');
insert into medicine_specifications (side_effects, daily_intake, structure) values ('Krvarenje, žutica','2 tablete na dan','varfarin');
insert into medicine_specifications (side_effects, daily_intake, structure) values ('Alergijska reakcija','12 tableta u podeljenim dozama','acetilsalicilna kiselina');
insert into medicine_specifications (side_effects, daily_intake, structure) values ('Teška vrtoglavica ili nesvestica, Bronhospazam','Jedna tableta','perindopril,amlodipin,indapamid');
insert into medicine_specifications (side_effects, daily_intake, structure) values ('Sistemska alergija, lokalna alergija, Lipodistrofija','Doktor daje instrukcije za dozu','insulin');

--medicine
insert into medicine (code, manufacturer, name, note, perscription, points, shape, specification_id, type) values ('AZ45', 'Hemofarm', 'Methyldopa', 'Može uzrokovati pospanost', 1, 3, 'pills', 1, 'Alfa-adrenergicki agonist');
insert into medicine (code, manufacturer, name, note, perscription, points, shape, specification_id, type) values ('BZ55', 'Kinezi', 'Sinopharm', 'vakcina', 1, 5, 'pills', 2, 'some type idl');
insert into medicine (code, manufacturer, name, note, perscription, points, shape, specification_id, type) values ('FAR-123', 'Galenika','Farin','Piti isključivo prema receptu',0,2,'tablets',3,'antikoagulans');
insert into medicine (code, manufacturer, name, note, perscription, points, shape, specification_id, type) values ('ANB-001', 'Galenika', 'Anbol', 'Ne uzimati ako uzimate lekove protiv zgrušavanja krvi npr. varfarin', 0, 1, 'tablets', 4, 'NSAIL');
insert into medicine (code, manufacturer, name, note, perscription, points, shape, specification_id, type) values ('C784','TAD PHARMA GMBH','Co-Amlessa','Čuvati na temperaturi do 30°C, u originalnom pakovanju radi zaštite od svetlosti i vlage',1,2,'tablets',5,'perindopril');
insert into medicine (code, manufacturer, name, note, perscription, points, shape, specification_id, type) values ('HUM010','ELI LILLY ITALIA S.P.A.','Humulin','Uložak od 3 mL se primenjuje samo u pen aplikatoru od 3 mL. Nije namenjen za primenu u penaplikatoru od 1,5 mL.',0,4,'saline',6,'insulini');

--replacement medicine
insert into medicine_replacement_medication (medicine_id, replacement_medication_id) values (1,2);
insert into medicine_replacement_medication (medicine_id, replacement_medication_id) values (1,3);
insert into medicine_replacement_medication (medicine_id, replacement_medication_id) values (1,4);
insert into medicine_replacement_medication (medicine_id, replacement_medication_id) values (2,1);
insert into medicine_replacement_medication (medicine_id, replacement_medication_id) values (3,1);
insert into medicine_replacement_medication (medicine_id, replacement_medication_id) values (4,1);
insert into medicine_replacement_medication (medicine_id, replacement_medication_id) values (2,4);

--pharmacies
insert into pharmacy (description, name, address_id, price_per_hour) values ('Otvoreni smo non-stop', 'Prima', 1, 40);
insert into pharmacy (description, name, address_id, price_per_hour) values ('', 'Apoteka Janković', 31, 42);
insert into pharmacy (description, name, address_id, price_per_hour) values ('Nema leka bez recepta!', 'Benu', 32, 46);
insert into pharmacy (description, name, address_id, price_per_hour) values ('Nedeljom ne radimo', 'Laurus', 33, 50);

-- pharmacy admin -password = sifrica
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (24, 'fa@maildrop.cc', true, 'Aleksandar', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '0635584712', 2, 'Vasić', LOCALTIMESTAMP);
insert into pharmacy_admin (id, pharmacy_id) values (14, 1);

--works
insert into work (doctor_id, start_hour, pharmacy_id, end_hour) values (11, '09:09:00', 1, '12:12:00');
insert into pharmacy_staff (pharmacy_id, staff_id) values (1, 1);
insert into work (doctor_id, start_hour, pharmacy_id, end_hour) values (12, '10:10:00', 1, '14:14:00');
insert into pharmacy_staff (pharmacy_id, staff_id) values (1, 2);
insert into work (doctor_id, start_hour, pharmacy_id, end_hour) values (11, '11:11:00', 2, '13:13:00');
insert into pharmacy_staff (pharmacy_id, staff_id) values (2, 3);

--med price
insert into med_price(price, start_date) values (200, current_timestamp - INTERVAL '10 days');
insert into med_price(price, start_date) values (100, current_timestamp - INTERVAL '10 days');
insert into med_price(price, start_date) values (250, current_timestamp - INTERVAL '10 days');
insert into med_price(price, start_date) values (150, current_timestamp - INTERVAL '10 days');

--med catalog in pharmacy
insert into medicine_in_pharmacy (in_stock, current_price_id, medicine_id, pharmacy_id) values (0, 1,1,1);
update med_price SET medicine_in_pharmacy_id = 1 WHERE id = 1;
insert into pharmacy_medicines (pharmacy_id, medicines_id) values (1,1);
--
insert into medicine_in_pharmacy (in_stock, current_price_id, medicine_id, pharmacy_id) values (10000, 2,2,1);
update med_price SET medicine_in_pharmacy_id = 2 WHERE id = 2;
insert into pharmacy_medicines (pharmacy_id, medicines_id) values (1,2);
--
insert into medicine_in_pharmacy (in_stock, current_price_id, medicine_id, pharmacy_id) values (300, 3,3,1);
update med_price SET medicine_in_pharmacy_id = 3 WHERE id = 3;
insert into pharmacy_medicines (pharmacy_id, medicines_id) values (1,3);
--
insert into medicine_in_pharmacy (in_stock, current_price_id, medicine_id, pharmacy_id) values (12000, 4,4,1);
update med_price SET medicine_in_pharmacy_id = 4 WHERE id = 4;
insert into pharmacy_medicines (pharmacy_id, medicines_id) values (1,4);

set timezone = 'Europe/Belgrade';
--appointments
-- id 1
--insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (11, 30, null, 1, 200, current_timestamp + INTERVAL '3day', 0);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (11, 30, null, 1, 300, '2021-06-01 08:00:00'::timestamp, 0);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (11, 30, null, 1, 400, '2021-06-03 08:00:00'::timestamp + INTERVAL '1 hour', 0);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (11, 30, null, 1, 500, '2021-06-04 08:00:00'::timestamp + INTERVAL '2 hours', 0);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (11, 30, null, 1, 330, '2021-06-01 08:00:00'::timestamp + INTERVAL '3 hours', 0);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (11, 30, null, 1, 340, '2021-06-17 08:00:00'::timestamp + INTERVAL '4 hours', 0);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (11, 30, null, 1, 350, '2021-05-17 08:00:00'::timestamp + INTERVAL '5 hours', 0);
insert into examination (status, appointment_id, patient_id) values (0, 1, 2);
insert into examination (diagnose, examination_info, status, appointment_id, patient_id) values ('Migrena', 'Pacijent oseca glavobolju', 1, 5, 1);
insert into examination (status, appointment_id, patient_id) values (0, 3, 3);
insert into examination (status, appointment_id, patient_id) values (3, 4, 2);
insert into examination (status, appointment_id, patient_id) values (0, 2, 1);
update appointment SET examination_id = 1 WHERE id = 1;
update appointment SET examination_id = 2 WHERE id = 2;
update appointment SET examination_id = 3 WHERE id = 3;
update appointment SET examination_id = 4 WHERE id = 4;
update appointment SET examination_id = 5 WHERE id = 5;


insert into patient_my_examinations (patient_id, my_examinations_id) values (1, 2);
insert into patient_my_examinations (patient_id, my_examinations_id) values (2, 1);
insert into patient_my_examinations (patient_id, my_examinations_id) values (3, 3);
insert into patient_my_examinations (patient_id, my_examinations_id) values (2, 4);
insert into patient_my_examinations (patient_id, my_examinations_id) values (1, 5);

--appointments for pharmacist
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (12, 30, null, 1, 300, '2021-06-01 08:00:00'::timestamp + INTERVAL '30 minutes', 1);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (12, 30, null, 1, 300, '2021-06-17 08:00:00'::timestamp + INTERVAL '1 hour 45 minutes', 1);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (12, 30, null, 1, 300, '2021-05-17 08:00:00'::timestamp + INTERVAL '2 hours 5 minutes', 1);
insert into examination (status, appointment_id, patient_id) values (0, 7, 2);
insert into examination (status, appointment_id, patient_id) values (0, 8, 1);
insert into examination (status, appointment_id, patient_id) values (2, 9, 3);
update appointment SET examination_id = 6 WHERE id = 7;
update appointment SET examination_id = 7 WHERE id = 8;
update appointment SET examination_id = 8 WHERE id = 9;
insert into patient_my_examinations (patient_id, my_examinations_id) values (1, 6);
insert into patient_my_examinations (patient_id, my_examinations_id) values (2, 7);
insert into patient_my_examinations (patient_id, my_examinations_id) values (3, 8);


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


-- doctor_working
insert into doctor_working (doctor_id, working_id) values (11, 1);
insert into doctor_working (doctor_id, working_id) values (12, 2);
insert into doctor_working (doctor_id, working_id) values (11, 3);

-- for filter dermatologist and pharmacist
        -- farm
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (2, 'masaGavr@maildrop.cc', true, 'Masa', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '0124567893', 5, 'Gavrilovic', LOCALTIMESTAMP);
insert into doctor (id) values (15);
insert into pharmacist (id) values (15);
        -- derm
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (2, 'ana@maildrop.cc', true, 'Ana', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '345826485', 4, 'Savin', LOCALTIMESTAMP);
insert into doctor (id) values (16);
insert into dermatologist (id) values (16);

insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (2, 'ana17@maildrop.cc', true, 'Anastasija', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '924601573', 4, 'Maric', LOCALTIMESTAMP);
insert into doctor (id) values (17);
insert into dermatologist (id) values (17);
        -- works
insert into work (doctor_id, start_hour, pharmacy_id, end_hour) values (15, '07:07:00', 1, '12:12:00');
insert into pharmacy_staff (pharmacy_id, staff_id) values (1, 4);
insert into work (doctor_id, start_hour, pharmacy_id, end_hour) values (16, '08:08:00', 1, '14:14:00');
insert into pharmacy_staff (pharmacy_id, staff_id) values (1, 5);
insert into work (doctor_id, start_hour, pharmacy_id, end_hour) values (17, '08:00:00', 1, '16:00:00');
insert into pharmacy_staff (pharmacy_id, staff_id) values (1, 6);
insert into work (doctor_id, start_hour, pharmacy_id, end_hour) values (16, '20:00:00', 2, '23:00:00');
insert into pharmacy_staff (pharmacy_id, staff_id) values (2, 7);
        -- doctor_working
insert into doctor_working (doctor_id, working_id) values (15, 4);
insert into doctor_working (doctor_id, working_id) values (16, 5);
insert into doctor_working (doctor_id, working_id) values (17, 6);
insert into doctor_working (doctor_id, working_id) values (16, 7);

--system administrator - password : sef
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (30, 'sissadmin@maildrop.cc', true, 'Mladen', '$2a$10$oJ1iLjwac72nZ8/sRNs1W.Er/tQuW8FxY80hveTjyDK8/7W0bOpAK', '0618878232', 1, 'Tasić', LOCALTIMESTAMP);
insert into sys_admin (id) values (18);


--suppliers
insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (16, 'milenkotep@maildrop.cc', true, 'Milenko', '$2a$10$0jpTwKzrxhpekj0zDufFvelVsfilpPXk7EX4U65u9gZfb7FTnOIlG', '0632212458', 6, 'Tepić', LOCALTIMESTAMP); --sifra : imamrobu
insert into supplier (id) values (19);


--MedQuantities (for orders)
insert into medicine_quantity(quantity, medicine_id) values (20, 1);
insert into medicine_quantity(quantity, medicine_id) values (30, 2);

--Orders
insert into medicine_order (end_date, start_date, author_id, chosen_offer_id, pharmacy_id) values (LOCALTIMESTAMP + interval '4' day, LOCALTIMESTAMP, 14, null, 1);
insert into medicine_order_all_medicines(medicine_order_id, all_medicines_id) values (1, 1);
insert into medicine_order_all_medicines(medicine_order_id, all_medicines_id) values (1, 2);


--MedQuantities (for suppliers)
insert into medicine_quantity(quantity, medicine_id) values (450, 1);
insert into medicine_quantity(quantity, medicine_id) values (500, 2);

--Suppliers' medicines in stock
insert into medicine_at_supplier(quantity_id, supplier_id) values (3, 19);
insert into supplier_medicines_in_stock(supplier_id, medicines_in_stock_id) values (19, 1);
insert into medicine_at_supplier(quantity_id, supplier_id) values (4, 19);
insert into supplier_medicines_in_stock(supplier_id, medicines_in_stock_id) values (19, 2);

--Suppliers' prices
insert into supplier_med_price(price, start_date, medicine_at_supplier_id) values (175, LOCALTIMESTAMP, 1);
update medicine_at_supplier SET supplier_price_id = 1 WHERE id = 1;
insert into supplier_med_price(price, start_date, medicine_at_supplier_id) values (85, LOCALTIMESTAMP, 2);
update medicine_at_supplier SET supplier_price_id = 2 WHERE id = 2;




-- New pharmacist

insert into users (address_id, email, enabled, name, password, phone_number, role_id, surname, last_password_reset_date) values (22, 'farm@maildrop.cc', true, 'Miki', '$2a$10$sRzil3y.isAJrvrT4dK5wOsACB8Y5SGqo7.dmfQK55dSn8wtRcQha', '0638895221', 5, 'Milan', LOCALTIMESTAMP);
insert into doctor (id) values (20);
insert into pharmacist (id) values (20);

insert into work (doctor_id, start_hour, pharmacy_id, end_hour) values (20, '09:09:00', 3, '12:12:00');
insert into pharmacy_staff (pharmacy_id, staff_id) values (3, 8);


-- Reservations
insert into med_reservation (code, last_date, quantity, reservation_date, status, medicine_in_pharmacy_id, patient_id, who_dispenses_id)
values ('fakecode1', current_timestamp - INTERVAL '1 days', 5, current_timestamp - INTERVAL '2 days', 0, 1, 1, null);
insert into med_reservation (code, last_date, quantity, reservation_date, status, medicine_in_pharmacy_id, patient_id, who_dispenses_id)
values ('fakecode2', current_timestamp + INTERVAL '2 days', 5, current_timestamp - INTERVAL '2 days', 0, 1, 1, null);

insert into patient_reservations (patient_id, reservations_id) values (1,1);
insert into patient_reservations (patient_id, reservations_id) values (1,2);
