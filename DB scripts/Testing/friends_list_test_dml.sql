insert into friend_list values ("emily", "bryanf", curdate(), "unaccepted");
insert into friend_list values ("bryanf", "dalton", curdate(), "unaccepted");
insert into friend_list values ("bryanf", "ryan", curdate(), "accepted");
insert into friend_list values ("ted", "bryanf", curdate(), "accepted");
insert into friend_list values ("ty", "ted", curdate(), "unaccepted");
insert into friend_list values ("dalton", "ryan", curdate(), "blocked");
insert into friend_list values ("ryan", "dalton", curdate(), "blocked"); -- should fail
insert into friend_list values ("ted", "ty", curdate(), "unaccepted"); -- should fail