DROP DATABASE IF EXISTS workshift;
CREATE DATABASE IF NOT EXISTS workshift;
USE workshift;

CREATE TABLE employee (
emp_id  varchar(3) unique not null,
emp_name varchar (20) not null,
emp_surname varchar (20) not null,
address varchar (30),
phonenumber varchar(10) not null,
email varchar (50),
deactivate boolean,
notes varchar(100),
CONSTRAINT emp_id_pk PRIMARY KEY (emp_id) 
);
drop table if exists repos ;
CREATE TABLE repos (
emp_id  varchar(3) not null,
dayOff1 date not null,
dayOff2 date not null,
CONSTRAINT ep_id_daysOff_pk PRIMARY KEY (emp_id, dayOff1, dayOff2), 
CONSTRAINT daysOff_fk FOREIGN KEY (emp_id) REFERENCES employee (emp_id) 
);

drop table if exists offwork;
CREATE TABLE offwork (
emp_id  varchar(3) not null,
timeOff date not null,
CONSTRAINT ep_id_timeOff_pk PRIMARY KEY (emp_id, timeOff),
CONSTRAINT timeOff_fk FOREIGN KEY (emp_id) REFERENCES employee (emp_id)  
);

drop table if exists sickLeave;
CREATE TABLE sickLeave (
emp_id  varchar(3) not null,
sick_leave date not null,
CONSTRAINT emp_id_sick_pk PRIMARY KEY (emp_id, sick_leave),
CONSTRAINT sick_fk FOREIGN KEY (emp_id) REFERENCES employee (emp_id)  
);

drop table if exists workshifts;
CREATE TABLE workshifts (
from_hour  time not null,
to_hour time not null,
noOfEmp int,
CONSTRAINT from_pk_to PRIMARY KEY (from_hour, to_hour)
 );
 
drop table if exists program;
CREATE TABLE program (
the_day date not null,
ws varchar(17) not null,
emp_id  varchar(3) not null,
CONSTRAINT ep_id_days_ws_pk PRIMARY KEY (emp_id, the_day, ws), 
CONSTRAINT e_fk FOREIGN KEY (emp_id) REFERENCES employee (emp_id) 
);

drop table if exists checkedMonths_andList;
CREATE TABLE checkedMonths_andList (
checked_month varchar (12),
empList varchar(100)
);