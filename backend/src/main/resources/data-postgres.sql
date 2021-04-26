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
insert into address (city, number, state, street) values ('Beograd', '41', 'Serbia', 'Milutina Milankoviča');
insert into address (city, number, state, street) values ('Novi Sad', '128', 'Serbia', 'Bulevar oslobođenja');
insert into address (city, number, state, street) values ('Novi Sad', '43', 'Serbia', 'Bulevar oslobođenja');
insert into address (city, number, state, street) values ('Novi Sad', '8', 'Serbia', 'Trg slobode');
insert into address (city, number, state, street) values ('Novi Sad', '20', 'Serbia', 'Stražilovska');
insert into address (city, number, state, street) values ('Novi Sad', '23', 'Serbia', 'Vuka Karadžića');

--patients
insert into users (address_id, email, name, password, phone_number, role, surname) values (10, 'oprincominero@maildrop.cc', 'Petar', 'peca', '0645597841', 0, 'Petrović');
insert into patient (points, id) values (10, 1);
insert into users (address_id, email, name, password, phone_number, role, surname) values (5, 'maya@maildrop.cc', 'Maja', 'sifra', '0617794512', 0, 'Marković');
insert into patient (points, id) values (1, 2);
insert into users (address_id, email, name, password, phone_number, role, surname) values (7, 'zija123@maildrop.cc', 'Živorad', 'teka', '0629745132', 0, 'Pantić');
insert into patient (points, id) values (12, 3);
insert into users (address_id, email, name, password, phone_number, role, surname) values (13, 'zki@maildrop.cc', 'Zoran', 'zoca', '0649985123', 0, 'Radovanović');
insert into patient (points, id) values (10, 4);
insert into users (address_id, email, name, password, phone_number, role, surname) values (19, 'natasha@maildrop.cc', 'Nataša', 'nata', '0635668741', 0, 'Bajunović');
insert into patient (points, id) values (1, 5);
insert into users (address_id, email, name, password, phone_number, role, surname) values (4, 'violet@maildrop.cc', 'Violeta', 'vixi', '0629541238', 0, 'Milutinović');
insert into patient (points, id) values (12, 6);
insert into users (address_id, email, name, password, phone_number, role, surname) values (20, 'gidra@maildrop.cc', 'Dragan', 'magi', '0619745231', 0, 'Petrović');
insert into patient (points, id) values (0, 7);
insert into users (address_id, email, name, password, phone_number, role, surname) values (15, 'vesna123@maildrop.cc', 'Vesna', 'veka', '0615217941', 0, 'Tomašević');
insert into patient (points, id) values (3, 8);
insert into users (address_id, email, name, password, phone_number, role, surname) values (2, 'abcdefgh@maildrop.cc', 'Mirko', 'miki', '0619874511', 0, 'Ivanić');
insert into patient (points, id) values (5, 9);
insert into users (address_id, email, name, password, phone_number, role, surname) values (6, 'extramail@maildrop.cc', 'Žarko', 'zax', '0649874771', 0, 'Nikolić');
insert into patient (points, id) values (0, 10);

--dermatologists
insert into users (address_id, email, name, password, phone_number, role, surname) values (11, 'gordop@maildrop.cc', 'Gordan', 'password', '0635521478', 1, 'Popović');
insert into doctor (id) values (11);
insert into dermatologist (id) values (11);

--pharmacists
insert into users (address_id, email, name, password, phone_number, role, surname) values (21, 'drmili@maildrop.cc', 'Milisav', 'password', '0612223855', 2, 'Nikolić');
insert into doctor (id) values (12);
insert into pharmacist (id) values (12);
insert into users (address_id, email, name, password, phone_number, role, surname) values (22, 'nadamm28@maildrop.cc', 'Nada', 'slojevito', '0638895221', 2, 'Macura');
insert into doctor (id) values (13);
insert into dermatologist (id) values (13);

--medicine
insert into medicine (code, manufacturer, name, note, perscription, points, shape, structure, type) values ('AZ45', 'Nemci', 'PHIIIZER', 'vakcina', 0, 3, 'PILL', 'struucturee', 'some type idl');
insert into medicine (code, manufacturer, name, note, perscription, points, shape, structure, type) values ('BZ55', 'Kinezi', 'Sinopharm', 'vakcina', 0, 5, 'PILL', 'struucturee', 'some type idl');
insert into medicine (code, manufacturer, name, note, perscription, points, shape, structure, type) values ('FAR-123', 'Galenika','Farin','Piti isključivo prema receptu',0,2,'tablets','varfarin','antikoagulans');

--pharmacies
insert into pharmacy (description, name, address_id) values ('Otvoreni smo non-stop', 'Prima', 1);
insert into pharmacy (description, name, address_id) values ('', 'Apoteka Janković', 31);
insert into pharmacy (description, name, address_id) values ('Nema leka bez recepta!', 'Benu', 32);
insert into pharmacy (description, name, address_id) values ('Nedeljom ne radimo', 'Laurus', 33);

-- pharmacy admin
insert into users (address_id, email, name, password, phone_number, role, surname) values (24, 'coa@maildrop.cc', 'Aleksandar', 'aaaaaa', '0635584712', 5, 'Vasić');
insert into pharmacy_admin (id, pharmacy_id) values (14, 1);

--works
insert into work (doctor_id, end_hour, pharmacy_id, start_hour) values (11, CURRENT_TIME, 1, CURRENT_TIME);
insert into pharmacy_staff (pharmacy_id, staff_id) values (1, 1);
insert into work (doctor_id, end_hour, pharmacy_id, start_hour) values (12, CURRENT_TIME, 1, CURRENT_TIME);
insert into pharmacy_staff (pharmacy_id, staff_id) values (1, 2);
insert into work (doctor_id, end_hour, pharmacy_id, start_hour) values (11, CURRENT_TIME, 2, CURRENT_TIME);
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
--insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (11, 30, null, 1, 200, current_timestamp + INTERVAL '3day', 0);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (11, 30, null, 1, 300, current_timestamp, 0);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (11, 30, null, 1, 400, current_timestamp, 0);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (11, 30, null, 1, 500, current_timestamp, 0);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (11, 30, null, 1, 330, current_timestamp, 0);
insert into appointment (doctor_id, duration_in_mins, examination_id, pharmacy_id, price, start_time, type) values (11, 30, null, 1, 340, current_timestamp, 0);
insert into examination (status, appointment_id, patient_id) values (0, 1, 2);
insert into examination (diagnose, examination_info, status, appointment_id, patient_id) values ('Migrena', 'Pacijent oseca glavobolju', 1, 2, 1);
insert into examination (status, appointment_id, patient_id) values (2, 3, 1);
insert into examination (status, appointment_id, patient_id) values (0, 4, 2);
insert into examination (status, appointment_id, patient_id) values (3, 5, 3);
update appointment SET examination_id = 1 WHERE id = 1;
update appointment SET examination_id = 2 WHERE id = 2;
update appointment SET examination_id = 3 WHERE id = 3;
update appointment SET examination_id = 4 WHERE id = 4;
update appointment SET examination_id = 5 WHERE id = 5;


insert into patient_my_examinations (patient_id, my_examinations_id) values (1, 2);
insert into patient_my_examinations (patient_id, my_examinations_id) values (2, 1);
insert into patient_my_examinations (patient_id, my_examinations_id) values (1, 3);
insert into patient_my_examinations (patient_id, my_examinations_id) values (2, 4);
insert into patient_my_examinations (patient_id, my_examinations_id) values (3, 5);


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


-- docrot_working
insert into doctor_working (doctor_id, working_id) values (11, 1);
insert into doctor_working (doctor_id, working_id) values (12, 2);
insert into doctor_working (doctor_id, working_id) values (11, 3);

-- for filter dermatologist and pharmacist
        -- farm
insert into users (address_id, email, name, password, phone_number, role, surname) values (2, 'nesto15@nesto.com', 'Masa', 'password10', '0124567893', 2, 'Gavrilovic');
insert into doctor (id) values (15);
insert into pharmacist (id) values (15);
        -- derm
insert into users (address_id, email, name, password, phone_number, role, surname) values (2, 'nesto16@nesto.com', 'Ana', 'password11', '345826485', 1, 'Savin');
insert into doctor (id) values (16);
insert into dermatologist (id) values (16);

insert into users (address_id, email, name, password, phone_number, role, surname) values (2, 'nesto17@nesto.com', 'Anastasija', 'password12', '924601573', 1, 'Maric');
insert into doctor (id) values (17);
insert into dermatologist (id) values (17);
        -- works
insert into work (doctor_id, end_hour, pharmacy_id, start_hour) values (15, CURRENT_TIME, 1, CURRENT_TIME);
insert into pharmacy_staff (pharmacy_id, staff_id) values (1, 4);
insert into work (doctor_id, end_hour, pharmacy_id, start_hour) values (16, CURRENT_TIME, 1, CURRENT_TIME);
insert into pharmacy_staff (pharmacy_id, staff_id) values (1, 5);
insert into work (doctor_id, end_hour, pharmacy_id, start_hour) values (17, CURRENT_TIME, 1, CURRENT_TIME);
insert into pharmacy_staff (pharmacy_id, staff_id) values (1, 6);
insert into work (doctor_id, end_hour, pharmacy_id, start_hour) values (16, CURRENT_TIME, 2, CURRENT_TIME);
insert into pharmacy_staff (pharmacy_id, staff_id) values (2, 7);
        -- doctor_working
insert into doctor_working (doctor_id, working_id) values (15, 4);
insert into doctor_working (doctor_id, working_id) values (16, 5);
insert into doctor_working (doctor_id, working_id) values (17, 6);
insert into doctor_working (doctor_id, working_id) values (16, 7);

