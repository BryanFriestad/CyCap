use db309sd1;
#drop table if exists player_reports;
create table player_reports(
	id int NOT NULL auto_increment,
	reporting_user varchar(255) NOT NULL,
    report_date date NOT NULL,
    offending_player varchar(255) NOT NULL,
    offense_date date NOT NULL,
    reason ENUM("cheat", "impersonation", "compromised", "unsportsmanlike", "inappropriate", "harassment", "other") NOT NULL,
    more_info varchar(500),
    primary key (id),
    foreign key (reporting_user) references account (UserID) on delete no action on update cascade,
    foreign key (offending_player) references account (UserID) on delete no action on update cascade
);
