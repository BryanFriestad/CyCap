package com.cycapservers.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cycapservers.account.Account;
import com.cycapservers.account.AccountRepository;
import com.cycapservers.account.friend_list.FriendListRepository;

@Controller
@SessionAttributes("account")
public class FriendListController {
	
	/**
	 * Autowires AccountsRepository interface for database connection
	 **/
	@Autowired
    private AccountRepository accountsRepository;

	/**
	 * Autowires FriendsRepository interface for database connection
	 */
    @Autowired
    private FriendListRepository friendsListRepo;
    
    private final Logger logger = LoggerFactory.getLogger(FriendListController.class);
    
    @GetMapping(value = "/accounts/block_list")
    public String showBlockedUsers(Model model, 
    		@SessionAttribute("account") Account account,
    		@RequestParam(name="unblock", required=false) String unblock_username
    ){
    	if(unblock_username != null){
    		System.out.printf("Unblocking user %s\r\n", unblock_username);
    		int status = friendsListRepo.UnblockUser(account.getUserID(), unblock_username);
    		if(status != 1)
    			logger.error("Failed to unblock user " + unblock_username);
    	}
    	
    	model.addAttribute("blocked", friendsListRepo.FindBlockedByUser(account.getUserID()));
    	model.addAttribute("account", account);
    	
    	return "accounts/block_list";
    }
    
    @GetMapping(value = "/accounts/friends")
    public String showFriendsList(Model model, 
    		@SessionAttribute("account") Account account,
    		@RequestParam(name="add", required=false) String un_add,
    		@RequestParam(name="block", required=false) String un_block,
    		@RequestParam(name="accept", required=false) String un_acpt,
    		@RequestParam(name="deny", required=false) String un_deny
    ){
		String action_message = null;
		
    	if(un_add != null){
    		Account user_to_add = accountsRepository.findOne(un_add);
    		if(user_to_add != null){
				int response = friendsListRepo.SendFriendRequest(account.getUserID(), un_add);
				if(response == 1){
					action_message = "Sent friend request to user " + un_add;
				}
				else if(response == -1) {
					action_message = "You are already friends with " + un_add + "!";
				}
				else if(response == -2) {
					action_message = "You cannot add user " + un_add + ". They have blocked you.";
				}
				else if(response == -3) {
					action_message = "You've already sent this user a request. Please patiently wait for them to respond.";
				}
				else if(response == -4) {
					action_message = "You have blocked user " + un_add + ". Unblock them before re-adding them.";
				}
				else if(response == -5) {
					action_message = "You cannot add youself as a friend, sorry :(";
				}
				else {
					action_message = "Unknown error! Please submit a bug report.";
				}
    		}
    		else{
    			action_message = "User " + un_add + " does not exist";
    		}
    	}
    	
    	if(un_block != null){
    		System.out.printf("Blocking user %s\r\n", un_block);
			Account user_to_block = accountsRepository.findOne(un_block);
    		if(user_to_block != null){
				int response = friendsListRepo.BlockUser(account.getUserID(), un_block);
				if(response >= 1) {
					action_message = "You have successfully blocked user " + un_block + ".";
				}
				else if(response == -1) {
					action_message = "You cannot block user " + un_block + ". They have already blocked you.";
				}
				else if(response == -2) {
					action_message = "You cannot block yourself. Have some more confidence friend";
				}
				else {
					action_message = "Unknown error! Please submit a bug report.";
				}
			}
			else{
    			action_message = "User " + un_block + " does not exist";
    		}
    	}
    	
    	if(un_deny != null){
    		System.out.printf("Denying user %s\r\n", un_deny);
    		int status = friendsListRepo.DenyFriendRequest(un_deny, account.getUserID());
    		if(status != 1)
    			logger.error("Failed to deny friend request from " + un_deny + " to " + account.getUserID());
    	}
    	else if(un_acpt != null){
    		System.out.printf("Accepting user %s\r\n", un_acpt);
    		int status = friendsListRepo.AcceptFriendRequest(un_acpt, account.getUserID());
    		if(status != 1)
    			logger.error("Failed to accept friend request from " + un_acpt + " to " + account.getUserID());
    	}
    	
		model.addAttribute("message", action_message); //adds a message for the user to see
    	model.addAttribute("acceptedFriends", friendsListRepo.FindAcceptedFriends(account.getUserID()));
    	model.addAttribute("pendingRcvd", friendsListRepo.FindPendingReceived(account.getUserID()));
    	model.addAttribute("pendingSent", friendsListRepo.FindPendingSent(account.getUserID()));
    	model.addAttribute("account", account);
    	return "accounts/friends";
    }

}
