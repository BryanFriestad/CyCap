package com.cycapservers.account.friend_list;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class FriendListId implements Serializable {
	private String sender;
	private String recipient;
	
	public FriendListId(){
		
	}
	
	public FriendListId(String sender, String recipient){
		this.sender = sender;
		this.recipient = recipient;
	}
	
	@Override
	public boolean equals(Object o){
		if(o == this)
			return true;
		
		if(!(o instanceof FriendListId))
			return false;
		
		FriendListId flid = (FriendListId) o;
		return flid.sender.equals(this.sender) && flid.recipient.equals(this.recipient);
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(sender, recipient);
	}
	
}
