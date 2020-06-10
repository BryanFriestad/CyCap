package com.cycapservers.news_reports;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="news_reports")
public class NewsReport {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull
    private String posting_dev;
    
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date post_date;
    
    @Transient
    private String byline; //Stores something like "May 20th, 2020 \n by: Dev #2"
    
    @NotNull
    private String hypertext;
    
    public NewsReport() {
    	
    }

	public NewsReport(int id, String posting_dev, Date post_Date, String byline, String hypertext) {
		super();
		this.id = id;
		this.posting_dev = posting_dev;
		this.post_date = post_Date;
		this.byline = byline;
		this.hypertext = hypertext;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPosting_dev() {
		return posting_dev;
	}

	public void setPosting_dev(String posting_dev) {
		this.posting_dev = posting_dev;
	}

	public Date getPost_Date() {
		return post_date;
	}

	public void setPost_Date(Date post_Date) {
		this.post_date = post_Date;
	}

	public String getByline() {
		String date = post_date.toString(); //in format yyyy-MM-dd
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("");
		
		String author = "by: " + posting_dev;
		
		String composite = date + "\n" + author;
		return composite;
	}

	public String getHypertext() {
		return hypertext;
	}

	public void setHypertext(String hypertext) {
		this.hypertext = hypertext;
	}
   
}