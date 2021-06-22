package com.cycapservers.game.matchmaking;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.cycapservers.BeanUtil;

@Component
public class MessageHandler extends TextWebSocketHandler 
{
	private LobbyManager lobby_manager = BeanUtil.getBean(LobbyManager.class);
	
	private ThreadLocal<String> UserId = new ThreadLocal<String>();
	
	private ThreadLocal<String> GameId = new ThreadLocal<String>();
	
	private ThreadLocal<Integer> Connect = new ThreadLocal<Integer>();
	
	/*
	 * 1. Look into thread local variables;
	 * 2. fix the scheduler
	 * 
	 * 
	 * 
	 */

    
	@Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception 
	{
		System.out.println("Socket Closed");
    }
	
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception 
    {
        Connect.set(1);
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception 
    {
    	if (Connect.get() != null)
    	{
    		if (Connect.get().equals(1))
    		{
        		Connect.set(0);
        	}
    	}
    	JSONObject message = new JSONObject(textMessage.getPayload());
    	switch (message.get("msg_type").toString())
    	{
    	case "join":
    		handleJoinGameMessage(session, message);
    		break;
    		
    	default:
    		break;
    	}
    }
    
    private void handleJoinGameMessage(WebSocketSession session, JSONObject message) throws IOException
    {
    	String client_id = message.getString("client_id");
    	String msg = lobby_manager.findLobbyOfUser(client_id).GetCurrentGameJoinMessage();
    	session.sendMessage(new TextMessage(msg));
    }
}