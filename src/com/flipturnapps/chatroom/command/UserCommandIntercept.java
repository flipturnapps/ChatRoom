package com.flipturnapps.chatroom.command;

import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.command.CommandOutput;
import com.flipturnapps.kevinLibrary.net.ClientData;


public class UserCommandIntercept extends UserCommandTemplate {

	public UserCommandIntercept(CommandOutput output) {
		super(output);
	}

	@Override
	public String getName() {
		return "intercept";
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
	public String[] getAliases() {
		return new String[]{"spy","socialspy","msgspy"};
	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		try
		{
			client.setCanIntercept(Boolean.parseBoolean(params[0]));
		}
		catch(Exception ex)
		{
			server.sendMessage(client,ServerMessenger.getDisplayTextLine("The argument should be a boolean.", 0, 0, 0));
		}

	}

	@Override
	protected void addPerms(ArrayList<String> perms) {

	}

}
