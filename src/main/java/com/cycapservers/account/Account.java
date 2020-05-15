package com.cycapservers.account;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.cycapservers.game.Utils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.hash.Hashing;

@Entity
@Table(name = "account")
public class Account {

	@Id
	@Column(name = "UserID")
	private String userID;
	
	@NotNull
	@Column(name = "salt")
	private String salt;
	
	@NotNull
	@Column(name = "hashed_password")
	private String hashed_password;
	
	@Transient
	private String plaintext_pw;
	
	@NotNull
	@Column(name = "Email")
	private String email;
	
	@NotNull
	@Column(name = "Creation_Date")
	// private DateTimeFormatter dateOfCreation;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateOfCreation;

	@NotNull
	@Column(name = "Member")
	private int member;

	@NotNull
	@Column(name = "Administrator")
	private int administrator;

	@NotNull
	@Column(name = "Developer")
	private int developer;

	public Account() {
		this.setDateOfCreation();
		this.member = 1;
		this.developer = 0;
		this.administrator = 0;
		
		String salt = Utils.createString(8); //generateSalt of 8 characters
		setSalt(salt); //setSalt
	}

	public String getUserID() {

		return this.userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getSalt(){
		return this.salt;
	}
	
	public void setSalt(String newSalt){
		this.salt = newSalt;
	}

	public String getPassword() {
		return this.hashed_password;
	}

	public void setPassword(String plaintext_passwd) {
		System.out.println("Plaintxt pw is " + plaintext_passwd);
		System.out.println("salt is " + getSalt());
		String salted_pw = plaintext_passwd + getSalt(); //concat plaintext_passwd with salt
		this.hashed_password = Hashing.sha256().hashString(salted_pw, StandardCharsets.UTF_8).toString();
	}

	public String getPlaintext_pw() {
		return plaintext_pw;
	}

	public void setPlaintext_pw(String plaintext_pw) {
		this.plaintext_pw = plaintext_pw;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfCreation() {
		return this.dateOfCreation;
	}

	public void setDateOfCreation() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		dtf.format(localDate);
		java.sql.Date dat = java.sql.Date.valueOf(localDate);
		this.dateOfCreation = dat;
	}
	
	public void setDateOfCreation(Date date) {
		this.dateOfCreation = date;
	}

	public int getMember() {
		return member;
	}

	public void setMember(int member) {
		this.member = member;
	}

	public int getAdministrator() {
		System.out.println(this.userID);
		return administrator;
	}

	public void setAdministrator(int administrator) {
		this.administrator = administrator;
	}

	public int getDeveloper() {
		System.out.println(this.userID);
		return developer;
	}

	public void setDeveloper(int developer) {
		this.developer = developer;
	}
}
