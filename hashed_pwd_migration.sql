use db309sd1;
create table temp_table like account;
insert temp_table select * from account;

insert into account (Id, UserID, salt, hashed_password, Creation_Date, Email, Member, Administrator, Developer)
select Id, UserID, substring(MD5(rand(Id)), -8), sha2(concat(Password, substring(MD5(rand(Id)), -8)), 256), Creation_Date, Email, Member, Administrator, Developer from temp_table;

drop table temp_table;
select Id, substring(MD5(rand(1)), -8) as salt, concat(UserID, substring(MD5(rand(1)), -8)) as salted_pw from account;

select sha2("password", 256);