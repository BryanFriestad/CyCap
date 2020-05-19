-- game table
#drop table if exists games;
create table games (
	game_id integer not null auto_increment,
    game_type ENUM("ctf", "tdm", "ffa") not null,
    start_date date not null,
    start_time time not null,
    winning_team integer,
    primary key (game_id)
);

-- game event table
#drop table if exists game_events;
create table game_events (
	game_id integer not null,
    sequence_order integer not null,
    event_type ENUM("kill", "grab", "return", "capture") not null,
    src_player varchar(255) not null,
    target_player varchar(255),
    weapon_used varchar(30),
    primary key (game_id, sequence_order),
    foreign key (game_id) references games(game_id) on delete cascade on update cascade,
    foreign key (src_player) references account(UserID) on delete no action on update cascade,
    foreign key (target_player) references account(UserID) on delete no action on update cascade
);

-- game players table
#drop table if exists game_players;
create table game_players (
	game_id integer not null,
    user_id varchar(255) not null,
    team integer not null,
    class_used varchar(20) not null,
    joined_late bool not null,
    left_early bool not null,
    primary key (game_id, user_id),
    foreign key (game_id) references games(game_id) on delete cascade on update cascade,
    foreign key (user_id) references account(UserID) on delete cascade on update cascade
);

-- create bot player accounts
insert into account values
("bot1", "saltsalt", sha2("bot1pw", 256), curdate(), "bot1@no.email", 0, 0, 0),
("bot2", "saltsalt", sha2("bot2pw", 256), curdate(), "bot2@no.email", 0, 0, 0),
("bot3", "saltsalt", sha2("bot3pw", 256), curdate(), "bot3@no.email", 0, 0, 0),
("bot4", "saltsalt", sha2("bot4pw", 256), curdate(), "bot4@no.email", 0, 0, 0),
("bot5", "saltsalt", sha2("bot5pw", 256), curdate(), "bot5@no.email", 0, 0, 0),
("bot6", "saltsalt", sha2("bot6pw", 256), curdate(), "bot6@no.email", 0, 0, 0),
("bot7", "saltsalt", sha2("bot7pw", 256), curdate(), "bot7@no.email", 0, 0, 0);