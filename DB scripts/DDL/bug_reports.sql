use db309sd1;
#drop table if exists bug_reports;
create table bug_reports(
	id int NOT NULL auto_increment,
	reporting_user varchar(255) NOT NULL,
    report_date date NOT NULL,
    short_description varchar(50) NOT NULL,
    location varchar(30) NOT NULL,
    encounter_date date NOT NULL,
    long_description varchar(500),
    primary key (id),
    foreign key (reporting_user) references account (UserID) on delete no action on update cascade
);
