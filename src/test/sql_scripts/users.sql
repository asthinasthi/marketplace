insert into app_user (first_name, last_name, password, username) values ('Seller2', 'Sellerson', '$2a$10$GoL4e7trQI6I6xo320xdnO5Ee6TUzOJlgENuyUPDSaYeAcOoG16/O', 'seller_2');
insert into app_user (first_name, last_name, password, username) values ('Seller3', 'Sellerson', '$2a$10$GoL4e7trQI6I6xo320xdnO5Ee6TUzOJlgENuyUPDSaYeAcOoG16/O', 'seller_3');

insert into seller(name, app_user_id,create_date, update_date, created_by, updated_by) values
('Seller1 Sellerson', 3, NOW(), NOW(), 'admin', 'admin');

insert into seller(name, app_user_id,create_date, update_date, created_by, updated_by) values
('Seller2 Sellerson', 4, NOW(), NOW(), 'admin', 'admin');

insert into seller(name, app_user_id,create_date, update_date, created_by, updated_by) values
('Seller3 Sellerson', 5, NOW(), NOW(), 'admin', 'admin');

insert into user_role(user_id, role_id) values (3, 3);
insert into user_role(user_id, role_id) values (4, 3);
insert into user_role(user_id, role_id) values (5, 3);


insert into app_user (first_name, last_name, password, username) values ('Buyer1', 'BuyerFord', '$2a$10$GoL4e7trQI6I6xo320xdnO5Ee6TUzOJlgENuyUPDSaYeAcOoG16/O', 'buyer_1');
insert into app_user (first_name, last_name, password, username) values ('Buyer2', 'BuyerFord', '$2a$10$GoL4e7trQI6I6xo320xdnO5Ee6TUzOJlgENuyUPDSaYeAcOoG16/O', 'buyer_2');
insert into app_user (first_name, last_name, password, username) values ('Buyer3', 'BuyerFord', '$2a$10$GoL4e7trQI6I6xo320xdnO5Ee6TUzOJlgENuyUPDSaYeAcOoG16/O', 'buyer_3');

insert into buyer(name, app_user_id,create_date, update_date, created_by, updated_by) values
('Buyer1 BuyerFord', 6, NOW(), NOW(), 'admin', 'admin');

insert into buyer(name, app_user_id,create_date, update_date, created_by, updated_by) values
('Buyer2 BuyerFord', 7, NOW(), NOW(), 'admin', 'admin');

insert into buyer(name, app_user_id,create_date, update_date, created_by, updated_by) values
('Buyer3 BuyerFord', 8, NOW(), NOW(), 'admin', 'admin');

insert into user_role(user_id, role_id) values (6, 4);
insert into user_role(user_id, role_id) values (7, 4);
insert into user_role(user_id, role_id) values (8, 4);
