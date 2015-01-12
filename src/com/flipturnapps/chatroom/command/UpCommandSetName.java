package com.flipturnapps.chatroom.command;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.net.ClientData;


public class UpCommandSetName extends UpCommandTemplate {

	
	@Override
	public String getName() {
		return "setname";
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
	protected void upExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		String old = client.getName();
		client.setName(params[0]);
		if(!(old == null || old.equals("")))
		{
			server.sendAll(ServerMessenger.getDisplayTextLine(old + "'s name has been set to " + params[0] + ".", 0,0,0));
		}
		else
		{
			server.sendAll(ServerMessenger.getDisplayTextLine(params[0] + " has joined.", 0,0,0));
		}
		server.refreshClientList();
		
		
	}

	

}
