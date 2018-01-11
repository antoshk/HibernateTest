
drop database if exists hibTest;
create database hibTest;
use hibTest;

create table t_hotels(f_id bigint auto_increment,f_name varchar(255),f_rating int,primary key(f_id));

create table t_rooms(f_id bigint auto_increment,f_number int,f_description varchar(255),f_price decimal,f_hotel_id bigint,primary key(f_id),foreign key(f_hotel_id) references t_hotels(f_id));

create table t_room_infos(f_room_id bigint,f_max_people int,f_has_shower tinyint(1),f_has_tv tinyint(1),primary key(f_room_id),foreign key(f_room_id) references t_rooms(f_id) ON delete cascade);

create table t_guests(f_id bigint auto_increment,f_full_name varchar(255),f_email varchar(255),primary key(f_id));

create table t_room_guests(f_room_id bigint,f_guest_id bigint,primary key(f_room_id, f_guest_id),foreign key (f_room_id) references t_rooms(f_id),foreign key (f_guest_id) references t_guests(f_id));

