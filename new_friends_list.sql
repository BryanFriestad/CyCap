use db309sd1;
#drop table if exists friend_list;
create table friend_list(
sender varchar(255),
recipient varchar(255),
date_of_relationship date NOT NULL,
r_status ENUM("unaccepted", "accepted", "denied", "blocked") NOT NULL,
primary key (sender, recipient),
foreign key (sender) references account (UserID) on delete cascade on update cascade,
foreign key (recipient) references account (UserID) on delete cascade on update cascade
);
