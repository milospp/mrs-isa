insert into address (id, city, number, state, street) values (1, 'Citty', 'Numbeer', 'Sstate', 'Street')
insert into address (id, city, number, state, street) values (2, 'Citty2', 'Numbeer2', 'Sstate2', 'Street2')
insert into address (id, city, number, state, street) values (3, 'Citty3', 'Numbeer3', 'Sstate3', 'Street3')


insert into users (id, address_id, email, name, password, phone_number, role, surname) values (1, 1, 'nesto@nesto.com', 'name', 'password', '012456789', 0, 'Surname')
insert into patient (points, id) values (10, 1)

insert into medicine (id, code, manufacturer, name, note, perscription, points, shape, structure, type) values (1, 'AZ45', 'PHIIIZER', 'Name naem', 'Take a note', 0, 3, 'PILL', 'struucturee', 'some type idl')

insert into pharmacy (id, description, name, address_id) values (1, 'Pharm Desc', 'Nooice Pharmacy', 2)
insert into pharmacy (id, description, name, address_id) values (2, 'GOod Pharm Desc', 'Veery Nooice Pharmacy', 3)


insert into med_price(id, price) values (1, 200)

insert into medicine_in_pharmacy (id, in_stock, current_price_id, medicine_id, pharmacy_id) values (1, 24, 1,1,1)
update med_price SET medicine_in_pharmacy_id = 1


insert into pharmacy_medicines (pharmacy_id, medicines_id) values (1,1)
