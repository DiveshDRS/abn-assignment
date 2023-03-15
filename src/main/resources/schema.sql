create table flight
(
    id integer,
    flight_number varchar(20) not null,
    origin varchar(6) not null,
    destination varchar(6) not null,
    departure_time varchar(6) not null,
    arrival_time varchar(6) not null,
    duration integer,
    price varchar(20) not null
);