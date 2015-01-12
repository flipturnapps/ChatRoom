package com.flipturnapps.chatroom.command;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.net.ClientData;


public class UpCommandSetOtherName extends UpCommandTemplate {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "setothername";
	}

	@Override
	public int getMaximumParams() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getMinimumParams() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	protected void upExecute(String[] params, ChatRoomServer server, ClientInfo client) 
	{
		System.out.println("set other name called");
		ClientInfo nameClient = server.getClientByName(params[0],server,client);
		if(nameClient != null)
		{
			nameClient.setName(params[1]);
			server.sendAll(ServerMessenger.getDisplayTextLine(params[0] + "'s name has been changed to " + params[1] + ".", 0, 0,0));
		}server.refreshClientList();

	}
	

}
