package com.cycapservers.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cycapservers.account.Account;
import com.cycapservers.account.AccountRepository;
import com.cycapservers.account.friend_list.FriendListRepository;
import com.cycapservers.bug_reports.BugReport;
import com.cycapservers.bug_reports.BugReportRepository;
import com.cycapservers.news_reports.NewsReport;
import com.cycapservers.news_reports.NewsReportRepository;

@Controller
@SessionAttributes("account")
public class InfoPagesController {
	
	@Autowired
	private BugReportRepository bugReportRepo;
	
	@Autowired
    private NewsReportRepository newsRepo;
	
	@ModelAttribute("account")
    public Account addUserAccount(){
		logger.info("Making new Account model attribute in InfoPagesController");
		return new Account();
    }
    
    private final Logger logger = LoggerFactory.getLogger(InfoPagesController.class);
    
    @GetMapping("bugs")
    public String getBugReportPage(Model model, @ModelAttribute("account") Account account){
    	if(account.getUserID() == null){
    			return "accounts/login";
    	}
    	
    	model.addAttribute("report", new BugReport());
    	return "info/bug_report";
    }
    
    @PostMapping("bug_submit")
    public String submitBugReport(Model model,
    		@ModelAttribute("account") Account account,
    		@ModelAttribute("report") BugReport report
    ){
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		dtf.format(localDate);
		java.sql.Date mysql_date = java.sql.Date.valueOf(localDate);
    	report.setReport_date(mysql_date);
    	report.setReporting_user(account.getUserID());
    	
    	bugReportRepo.save(report);
    	
    	return "info/bug_submit";
    }
    
    @GetMapping("news")
    public String dev_updates_get(Model model, @RequestParam(name="count", required=false) Integer count){
    	
    	List<NewsReport> reports;
    	
    	if(count != null){
    		reports = newsRepo.GetRecentNews(count);
    	}
    	else{
    		reports = newsRepo.GetRecentNews(10);
    	}
    	
    	model.addAttribute("reports", reports);
    	
    	return "/info/news_reports";
    }
    
    @GetMapping("add_news")
    public String addNewsReportGet(){
    	return"/info/coming_soon";
    }
    
    @GetMapping("about")
    public String about_the_team() {
    	return "/about";
    }

}
