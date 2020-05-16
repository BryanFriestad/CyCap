use db309sd1;
#drop table if exists friend_list;
create table friend_list(
sender varchar(255) NOT NULL,
recipient varchar(255) NOT NULL,
date_of_relationship date NOT NULL,
r_status ENUM("unaccepted", "accepted", "blocked") NOT NULL,
primary key (sender, recipient),
foreign key (sender) references account (UserID) on delete cascade on update cascade,
foreign key (recipient) references account (UserID) on delete cascade on update cascade
);

#drop trigger if exists CheckSenderRecipientSwap;
DELIMITER //
create trigger CheckSenderRecipientSwap
	before insert on friend_list
    for each row
    BEGIN
		if exists (select * from friend_list where sender = new.recipient and recipient = new.sender) then
			signal sqlstate '45000'
            set MESSAGE_TEXT = "Sender-Recipient pair already exists in reverse";
        end if;
    END //
DELIMITER ;

#drop trigger if exists CheckSenderRecipientMatch;
DELIMITER //
create trigger CheckSenderRecipientMatch
	before insert on friend_list
	for each row
	BEGIN
		if new.sender = new.recipient then
			signal sqlstate '45000'
			set MESSAGE_TEXT = "Sender is the same as the recipient";
		end if;
	END //
DELIMITER ;

#drop procedure if exists FindAcceptedFriends;
DELIMITER //
create procedure FindAcceptedFriends(in username varchar(255))
	BEGIN
	select case when sender = username
				then recipient
				else sender
			end as Friend,
			date_of_relationship
	from friend_list where
	(sender = username or recipient = username) 
    and r_status = "accepted"
    order by date_of_relationship desc;
	END //
DELIMITER ;

#drop procedure if exists FindPendingReceived;
DELIMITER //
create procedure FindPendingReceived(in username varchar(255))
	BEGIN
	select sender from friend_list where
	recipient = username and r_status = "unaccepted"
    order by date_of_relationship desc;
	END //
DELIMITER ;

#drop procedure if exists FindPendingSent;
DELIMITER //
create procedure FindPendingSent(in username varchar(255))
	BEGIN
	select recipient, date_of_relationship
	from friend_list where
	sender = username and r_status = "unaccepted"
    order by date_of_relationship desc;
	END //
DELIMITER ;

#drop procedure if exists FindBlockedByUser;
DELIMITER //
create procedure FindBlockedByUser(in username varchar(255))
	BEGIN
	select recipient as Blocked_User
	from friend_list where
	sender = username and r_status = "blocked";
	END //
DELIMITER ;

#drop procedure if exists SendFriendRequest;
DELIMITER //
create procedure SendFriendRequest(in sender_un varchar(255), in rec_un varchar(255))
	BEGIN
	if exists (select * from friend_list where sender = rec_un and recipient = sender_un and r_status = "unaccepted") then
		update friend_list set r_status = "accepted" where sender = rec_un and recipient = sender_un;
		select row_count();
	else
		insert into friend_list values (sender_un, rec_un, curdate(), "unaccepted");
		select row_count();
	end if;
    END //
DELIMITER ;

#drop procedure if exists AcceptFriendRequest;
DELIMITER //
create procedure AcceptFriendRequest(in sender_un varchar(255), in rec_un varchar(255))
	BEGIN
    update friend_list set r_status = "accepted", date_of_relationship = curdate() 
    where sender = sender_un and recipient = rec_un and r_status = "unaccepted";
    select row_count();
    END //
DELIMITER ;

#drop procedure if exists DenyFriendRequest;
DELIMITER //
create procedure DenyFriendRequest(in sender_un varchar(255), in rec_un varchar(255))
	BEGIN
    delete from friend_list
    where sender = sender_un and recipient = rec_un and r_status = "unaccepted";
    select row_count();
    END //
DELIMITER ;

#drop procedure if exists BlockUser;
DELIMITER //
create procedure BlockUser(in sender_un varchar(255), in blocked_un varchar(255))
	BEGIN
    if exists (select * from friend_list where sender = blocked_un and recipient = sender_un) then
		if exists (select * from friend_list where sender = blocked_un and recipient = sender_un and r_status = "blocked") then
			select -1;
		else
			delete from friend_list where sender = blocked_un and recipient = sender_un;
			
			insert into friend_list values (sender_un, blocked_un, curdate(), "blocked")
			on duplicate key update r_status = "blocked", date_of_relationship = curdate();
			select row_count();
		end if;
	else
		insert into friend_list values (sender_un, blocked_un, curdate(), "blocked") 
		on duplicate key update r_status = "blocked", date_of_relationship = curdate();
		select row_count();
	end if;
    END //
DELIMITER ;

#drop procedure if exists UnblockUser;
DELIMITER //
create procedure UnblockUser(in sender_un varchar(255), in unblock_un varchar(255))
	BEGIN
    delete from friend_list
    where sender = sender_un and recipient = unblock_un and r_status = "blocked";
    select row_count();
    END //
DELIMITER ;
    