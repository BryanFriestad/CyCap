-- insert games
insert into games (game_type, start_date, start_time, winning_team)
values ("ctf", '2020-04-29', '11:00:00', 1),
("tdm", '2020-05-09', '13:00:00', 2),
("ctf", '2020-05-11', '15:30:00', 2);

-- insert game players
insert into game_players (game_id, user_id, team, class_used)
values (1, "bryanf", 2, "recruit"),
(1, "bot3", 2, "recruit"),
(1, "bryanf", 2, "recruit"),
(1, "bryanf", 2, "recruit"),
(1, "bryanf", 2, "recruit"),
(1, "bryanf", 2, "recruit"),
(1, "bryanf", 2, "recruit"),
(1, "bryanf", 2, "recruit"),
(1, "bryanf", 2, "recruit"),
(1, "bryanf", 2, "recruit");
