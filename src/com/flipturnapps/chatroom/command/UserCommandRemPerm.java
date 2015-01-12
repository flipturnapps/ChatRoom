package com.flipturnapps.chatroom.command;

import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.kevinLibrary.command.CommandOutput;
import com.flipturnapps.kevinLibrary.net.ClientData;


public class UserCommandRemPerm extends UserCommandTemplate 
{

	public UserCommandRemPerm(CommandOutput output) {
		super(output);
	}

	@Override
	public String getName() {
		return "remperm";
	}

	@Override
	public String getHelpText() {
		return "remperm <name> <perm>";
	}

	@Override
	public int getMaximumParams() 
	{
		return 2;
	}

	@Override
	public int getMinimumParams() 
	{
		return 2;
	}

	@Override
	public String[] getAliases() 
	{
		return null;
	}



	@Override
	protected void addPerms(ArrayList<String> perms)
	{
		perms.add("perms");

	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server,
			ClientInfo client) 
	{
		ClientInfo found = server.getClientByName(params[0], server, client);
		if(found != null)
		{
			found.remPerm(params[1]);
			
		}

	}

}
