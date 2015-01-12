package com.flipturnapps.chatroom.command;

import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.command.CommandOutput;

public class UserCommandSetFieldText extends UserCommandTemplate {

	private static final String NAME = "setfieldtext";

	public UserCommandSetFieldText(CommandOutput output) {
		super(output);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getMaximumParams() 
	{
		return Integer.MAX_VALUE;
	}

	@Override
	public int getMinimumParams() 
	{
		return 1;
	}

	@Override
	public String[] getAliases() 
	{
		return new String[]{"st","settext"};
	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server, ClientInfo client) 
	{
		ClientInfo setClient = server.getClientByName(params[0], server, client);
		if(setClient == null)
			return;
		String setString = "";
		for(int i = 1; i < params.length; i++)
		{
			setString += params[i];
			if(i!=params.length -1)
				setString += " ";
		}
		server.sendMessage(setClient, ServerMessenger.setClientFieldText(setString));
		setClient.sendDisplayString(client.getName() + " has set your text field text.");
		client.sendDisplayString("You have set " + setClient.getName() + "'s text field text.");

	}

	@Override
	protected void addPerms(ArrayList<String> perms) {

	}

}
