package com.flipturnapps.chatroom.command;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.net.ClientData;


public class UpCommandSay extends UpCommandTemplate {

	@Override
	public String getName() {
		return "say";
	}

	@Override
	public int getMaximumParams() {
		return 1;
	}

	@Override
	public int getMinimumParams() {
		return 1;
	}

	@Override
	protected void upExecute(String[] params, ChatRoomServer server, ClientInfo client) 
	{
		server.sendAll(ServerMessenger.getDisplayTextLine(params[0], client));
		
	}

	

}
