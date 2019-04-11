use employees;

CREATE TABLE employee (
    id bigint not null auto_increment,
    date_of_birth date not null,
    date_of_employment date not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    middle_initial varchar(1),
    status varchar(20) not null,
    primary key (id)
);
