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
import com.cycapservers.game.Team;
import com.cycapservers.game.CharacterClass;
import com.cycapservers.game.Utils;
import com.cycapservers.game.matchmaking.Lobby;
import com.cycapservers.game.matchmaking.LobbyManager;
import com.cycapservers.game.matchmaking.LobbyType;


@Controller
@SessionAttributes("account")
public class GameController {

	@Autowired
	private LobbyManager lobby_manager;
	
    private final Logger logger = LoggerFactory.getLogger(GameController.class);
    
    @ModelAttribute("account")
    public Account newAccount()
    {
    	logger.info("Making new Account model attribute in GameController");
    	return new Account();
    }
    
    @GetMapping("game_list")
    public String gameListPage(Model model, @ModelAttribute("account") Account account) 
    {
    	if (account.getUserID() != null) 
    	{
    		model.addAttribute("types", lobby_manager.getAvailableLobbyTypes());
    		model.addAttribute("counts", lobby_manager.getPlayerCountsByLobbyType());
    		return "game/game_list";
    	}
    	else 
    	{
    		logger.info("Entered into get accounts login controller Layer");
        	return "accounts/login";
    	}
    }
    
    @GetMapping("find_lobby")
    public String getLobby(@ModelAttribute("account") Account account,
    					   @RequestParam(name="type", required=true) LobbyType lobby_type)
    {
    	if (account.getUserID() != null) 
    	{
	    	Lobby l = lobby_manager.findValidLobby(account.getUserID(), lobby_type);
	    	l.joinLobby(account.getUserID());
	    	return "redirect:/lobby";
    	}
    	else
    	{
    		logger.info("Entered into get accounts login controller Layer");
        	return "accounts/login";
    	}
    }
    
    @GetMapping("play")
    public String playNow(Model model, @ModelAttribute("account") Account account) 
    {
    	if (account.getUserID() != null) 
    	{
	    	model.addAttribute("user", account.getUserID());
	    	return "play";
    	}
    	else 
    	{
    		logger.info("Entered into get accounts login controller Layer");
        	return "accounts/login";
    	}
    }
    
    @GetMapping("lobby")
    public String Lobby(Model model, @ModelAttribute("account") Account account)
    {
    	if(account.getUserID() != null) 
    	{
    		//info to add to lobby page model
    		//String - timer.tostring
    		//Char_class my_class
    		//int my team
    		//List<char_class> available_classes (to switch to)
    		//List<string> player ids
    		//hashmap<string, int> team nums
    		//hashmap<string, char_class> character classes
    		
    		Lobby l = lobby_manager.findLobbyOfUser(account.getUserID()); //find lobby for given player
    		if (l == null) return "redirect:/new_game_list"; //if it doesnt exist, kick them back to the find_game page
    		
    		if (l.GetTimeRemaining() <= 0) return "redirect:/play";
    		
    		model.addAttribute("lobby", l);
    		model.addAttribute("my_class", l.GetClassOfPlayer(account.getUserID()));
    		model.addAttribute("available_classes", CharacterClass.values()); //TODO: replace this with a lookup to the DB
    		model.addAttribute("players", l.GetPlayersInLobby());
    		
    		return "game/lobby";
    	}
    	else 
    	{
    		logger.info("Entered into get accounts login controller Layer");
        	return "accounts/login";
    	}
    }
}