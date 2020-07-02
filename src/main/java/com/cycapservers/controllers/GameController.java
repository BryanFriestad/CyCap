package com.cycapservers.controllers;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cycapservers.BeanUtil;
import com.cycapservers.account.Account;
import com.cycapservers.account.AccountRepository;
import com.cycapservers.game.LobbyManager;
import com.cycapservers.game.LobbyType;
import com.cycapservers.game.Utils;


@Controller
@SessionAttributes("account")
public class GameController {

    private final Logger logger = LoggerFactory.getLogger(GameController.class);
    
    @ModelAttribute("account")
    public Account newAccount(){
    	logger.info("Making new Account model attribute in GameController");
    	return new Account();
    }
    
    @GetMapping("new_game_list")
    public String gameListPage(Model model, @ModelAttribute("account") Account account) {
    	if(account.getUserID() != null) {
    		model.addAttribute("games", BeanUtil.getBean(LobbyManager.class).getAvailableLobbyTypes());
    		return "game/game_list";
    	}
    	else {
    		logger.info("Entered into get accounts login controller Layer");
        	return "accounts/login";
    	}
    }
    
    @GetMapping("find_lobby")
    public String getLobby(@ModelAttribute("account") Account account,
    					   @RequestParam(name="type", required=true) LobbyType lobby_type
    ){
    	System.out.println("Lobby type: " + lobby_type);
    	return "info/coming_soon";
    }
    
    @GetMapping("play")
    public String playNow(Model model, @ModelAttribute("account") Account account) {
    	if(account.getUserID() != null) {
	    	model.addAttribute("user", account.getUserID());
	    	return "play";
    	}
    	else {
	    	Random rand = new Random();
	    	model.addAttribute("user", "guest" + rand.nextInt(1000000));
	    	return "play";
    	}
    }
    
    @GetMapping("new_lobby")
    public String Lobby(@ModelAttribute("account") Account account){
    	if(account.getUserID() != null) {
    		return "game_list2";
    	}
    	else {
    		logger.info("Entered into get accounts login controller Layer");
        	return "accounts/login";
    	}
    }
    
    @GetMapping("/LobbyScreen")
    public String LobbyScreen(@ModelAttribute("account") Account account){
    	if(account.getUserID() != null) {
    		return "LobbyScreen";
    	}
    	else {
    		logger.info("Entered into get accounts login controller Layer");
        	return "accounts/login";
    	}
    }
}