package com.cycapservers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cycapservers.account.Account;
import com.google.common.hash.Hashing;

public class CustomMappingExceptionResolver implements HandlerExceptionResolver 
{
	private final Logger logger;
	
	public CustomMappingExceptionResolver() 
	{
		logger = LoggerFactory.getLogger(CustomMappingExceptionResolver.class);
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) 
	{
		ModelAndView mav = new ModelAndView();
		
		Account account;
		Object obj = req.getSession().getAttribute("account");
		if(obj != null && obj instanceof Account){
			account = (Account) obj;
		}
		else{
			account = new Account();
			req.getSession().setAttribute("account", account);
		}
		
		mav.setViewName("/error/error-500");
		
		LocalDateTime now = LocalDateTime.now();
    	String now_string = now.toString();
    	@SuppressWarnings("deprecation")
		String exception_id = Hashing.sha1().hashString(now_string, StandardCharsets.UTF_8).toString(); //doesn't need to be secure, only unique
    	
    	String stackTrace = "";
    	for(StackTraceElement ste : ex.getStackTrace()){
    		stackTrace += "\t\t" + ste.toString() + "\n";
    	}
    	
    	logger.error("ID: " + exception_id + "\n\tRequest: " + req.getRequestURL() + "\n\tRaised Exception: " + ex + "\n\tStack Trace:\n" + stackTrace);
    	
    	mav.addObject("error_id", exception_id);
    	mav.addObject("account", account);
		
		return mav;
	}

}