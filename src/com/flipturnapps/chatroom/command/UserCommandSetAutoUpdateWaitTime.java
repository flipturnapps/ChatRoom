package com.flipturnapps.chatroom.command;

import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.command.CommandOutput;

public class UserCommandSetAutoUpdateWaitTime extends UserCommandTemplate {

	public UserCommandSetAutoUpdateWaitTime(CommandOutput output) {
		super(output);

	}

	@Override
	public String getName() 
	{
		return "setautoupdatewaittime";
	}

	@Override
	public int getMaximumParams()
	{
		return 1;
	}

	@Override
	public int getMinimumParams() 
	{
		return 1;
	}

	@Override
	public String[] getAliases() {
		return new String[] {"delay","setdelay","setwaittime",};
	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server, ClientInfo client)
	{
		try
		{
			server.setAutoUpdateWaitTime(Long.parseLong(params[0]));
		}
		catch(Exception ex)
		{
			server.sendMessage(client, ServerMessenger.getDisplayTextLine("Corrupt Time!", 0, 0, 0));
		}

	}

	@Override
	protected void addPerms(ArrayList<String> perms) 
	{
		
	}

}
