-- get accepted friends and DoR
select case when sender = "bryanf"
			then recipient
			else sender
	   end as Friend,
       date_of_relationship
from friend_list where
(sender = "bryanf" or recipient = "bryanf") and
r_status = "accepted";

-- get received pending friend requests
select sender from friend_list where
recipient = "bryanf" and r_status = "unaccepted";

-- get sent pending friend requests
select recipient, date_of_relationship
from friend_list where
sender = "bryanf" and r_status = "unaccepted";

-- get blocked users
select recipient as Blocked_User
from friend_list where
sender = "dalton" and r_status = "blocked";