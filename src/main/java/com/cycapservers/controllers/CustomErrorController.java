package com.cycapservers.controllers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cycapservers.account.Account;
import com.google.common.hash.Hashing;

@Controller
@SessionAttributes("account")
public class CustomErrorController implements ErrorController{
    
    private final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);
    
    @GetMapping("/500test")
    public String serverErrorTest() throws Exception{
    	throw new Exception("Test Exception");
    }
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(@SessionAttribute("account") Account account, HttpServletRequest req, Exception ex) {
    	
    	LocalDateTime now = LocalDateTime.now();
    	String now_string = now.toString();
    	@SuppressWarnings("deprecation")
		String exception_id = Hashing.sha1().hashString(now_string, StandardCharsets.UTF_8).toString(); //doesn't need to be secure, only unique
    	
    	String stackTrace = "";
    	for(StackTraceElement ste : ex.getStackTrace()){
    		stackTrace += "\t\t" + ste.toString() + "\n";
    	}
    	
    	logger.error("ID: " + exception_id + "\n\tRequest: " + req.getRequestURL() + "\n\tRaised Exception: " + ex + "\n\tStack Trace:\n" + stackTrace);

    	ModelAndView mav = new ModelAndView();
    	mav.addObject("error_id", exception_id);
    	mav.addObject("account", account);
    	mav.setViewName("errors/error-500");
    	return mav;
    }
    
    @RequestMapping("/error")
    public String handleError(@SessionAttribute("account") Account account, Model model, HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
         
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
         
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "errors/error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "errors/error-500";
            }
        }
        return "errors/generic-error";
    }
    
    

	@Override
	public String getErrorPath() {
		return "errors/generic-error";
	}

}
