package com.cycapservers.account;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="friend_list")
@IdClass(FriendListId.class)
public class FriendList {
    
	@Id
    private String sender;
    
    @Id
    private String recipient;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date_of_relationship;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private RelationshipStatus r_status;
    
    public FriendList(){
    	
    }

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public Date getDate_of_relationship() {
		return date_of_relationship;
	}

	public void setDate_of_relationship(Date date_of_relationship) {
		this.date_of_relationship = date_of_relationship;
	}
	
	public void setDate_of_relationship() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		dtf.format(localDate);
		java.sql.Date mysql_date = java.sql.Date.valueOf(localDate);
		this.date_of_relationship = mysql_date;
	}

	public RelationshipStatus getR_status() {
		return r_status;
	}

	public void setR_status(RelationshipStatus r_status) {
		this.r_status = r_status;
	}
    
}