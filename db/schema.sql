-- drop database if exist cpDatabase;

create database carparkDB;

use carparkDB;

create table user (
    userId varchar(64) not null,
    password varchar(128) not null,
    email varchar(64) PRIMARY KEY not null 
    -- primary key(userId);
);

create table cp_info (
    car_park_no varchar(16),
    address varchar(256),
    x_coord VARCHAR(128),
    y_coord VARCHAR(128),
    car_park_type varchar(256),
    type_of_parking_system varchar(256),
    short_term_parking varchar(256),
    free_parking varchar(256),
    night_parking varchar(256),
    car_park_decks int,
    gantry_height dec(3,2),
    car_park_basement enum('Y','N'),

    primary key(car_park_no)
);

create table favourite ( 
    favId int auto_increment not null,
    email varchar(64) not null, 
    car_park_no varchar(64) not null,
    address varchar(256) not null

    primary key(favId),

    constraint fk_email
    foreign key(email) references user(email),

    constraint fk_car_park_no
    foreign key(car_park_no) references cp_info(car_park_no)
);

create table report (
	report_id int auto_increment,
    email varchar(64) not null,
    car_park_no varchar(64) not null,
    description varchar(128) not null,
    media_type varchar(64) not null,
    pic mediumblob not null,
    
    primary key(report_id)
    
);