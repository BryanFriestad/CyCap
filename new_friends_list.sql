use db309sd1;
create table friend_list(
sender varchar(255),
recipient varchar(255),
date_of_relationship date NOT NULL,
r_status ENUM("unaccepted", "accepted", "denied", "blocked") NOT NULL,
primary key (sender, recipient)
);