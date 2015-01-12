package com.flipturnapps.chatroom.command;

import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ClientMessenger;
import com.flipturnapps.kevinLibrary.command.CommandOutput;


public class UserCommandRename extends UserCommandTemplate {

	public UserCommandRename(CommandOutput output) {
		super(output);
	}

	@Override
	public String getName() 
	{
		return "rename";
	}

	@Override
	public String getHelpText() 
	{
		return "Usage: rename [oldName] <newName> - Renames a member.";
	}

	@Override
	public int getMaximumParams() 
	{
		return 2;
	}

	@Override
	public int getMinimumParams() 
	{
		return 1;
	}

	@Override
	public String[] getAliases() 
	{
		return new String[] {"setun","setname","setusername"};
	}


	

	@Override
	protected void addPerms(ArrayList<String> perms) 
	{
		perms.add("mod");
		perms.add("rename");
		
	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		if(params.length == 1)
		{
			server.newMessage(ClientMessenger.getSetName(params[0]),client);
		}
		else
		{
			server.newMessage(ClientMessenger.getSetOtherName(params[0],params[1]),client);
		}
		
	}

}
