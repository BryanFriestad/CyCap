package com.cycapservers.bug_reports;

import java.sql.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="bug_reports")
public class BugReport {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull
    private String reporting_user;
    
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date report_date;
    
    @NotNull
    private String short_description;
    
    @NotNull
    private String location;
    
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date encounter_date;
    
    private String long_description;
    
    public BugReport(){
    	
    }

	public BugReport(int id, String reporting_user, Date report_date, String short_description, String location, Date encounter_date, String long_description) {
		this.id = id;
		this.reporting_user = reporting_user;
		this.report_date = report_date;
		this.short_description = short_description;
		this.location = location;
		this.encounter_date = encounter_date;
		this.long_description = long_description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReporting_user() {
		return reporting_user;
	}

	public void setReporting_user(String reporting_user) {
		this.reporting_user = reporting_user;
	}

	public Date getReport_date() {
		return report_date;
	}

	public void setReport_date(Date report_date) {
		this.report_date = report_date;
	}

	public String getShort_description() {
		return short_description;
	}

	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getEncounter_date() {
		return encounter_date;
	}

	public void setEncounter_date(Date encounter_date) {
		this.encounter_date = encounter_date;
	}

	public String getLong_description() {
		return long_description;
	}

	public void setLong_description(String long_description) {
		this.long_description = long_description;
	}
    
}