package com.cycapservers.player_reporting;

import java.sql.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="player_reports")
public class PlayerReport {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull
    private String reporting_user;
    
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date report_date;
    
    @NotNull
    private String offending_player;
    
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date offense_date;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private ReportReason reason;
    
    private String more_info;
    
    public PlayerReport(){
    	
    }

	public PlayerReport(int id, String reporting_user, Date report_date, String offending_player, Date offense_date,
			ReportReason reason, String long_description) {
		this.id = id;
		this.reporting_user = reporting_user;
		this.report_date = report_date;
		this.offending_player = offending_player;
		this.offense_date = offense_date;
		this.reason = reason;
		this.more_info = long_description;
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

	public String getOffending_player() {
		return offending_player;
	}

	public void setOffending_player(String offending_player) {
		this.offending_player = offending_player;
	}

	public Date getOffense_date() {
		return offense_date;
	}

	public void setOffense_date(Date offense_date) {
		this.offense_date = offense_date;
	}

	public ReportReason getReason() {
		return reason;
	}

	public void setReason(ReportReason reason) {
		this.reason = reason;
	}

	public String getMore_info() {
		return more_info;
	}

	public void setMore_info(String more_info) {
		this.more_info = more_info;
	}
	
}