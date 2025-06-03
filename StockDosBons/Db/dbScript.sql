create database sdbdb;

use sdbdb;

create table typePro_tb(
	idType integer primary key,
    type varchar(45)
);

create table product_tb(
	idPro integer primary key not null auto_increment,
    namePro varchar(100) not null,
    pricePro decimal(10,2) not null,
    quantityPro int not null,
    type_id integer,
    foreign key (type_id) references typePro_tb(idType)
);

create table furniture_tb(
	pro_id int primary key,
    heightFur decimal not null,
    widthFur decimal not null,
    materialFur varchar(65) not null,
    foreign key (pro_id) references product_tb(idPro)
);

create table hortifruti_tb(
	pro_id int primary key,
    middleWeightHor decimal(10,2) not null,
    foreign key (pro_id) references product_tb(idPro)
);

create table electronic_tb(
	pro_id int primary key,
    marEle varchar(45) not null,
    fabricatorEle varchar(65) not null,
    modelEle varchar(100) not null,
    yearLaunchEle date not null,
    foreign key (pro_id) references product_tb(idPro)
);