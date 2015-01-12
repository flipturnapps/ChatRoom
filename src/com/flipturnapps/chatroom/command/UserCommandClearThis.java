package com.flipturnapps.chatroom.command;

import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.command.CommandOutput;

public class UserCommandClearThis extends UserCommandTemplate {

	public UserCommandClearThis(CommandOutput output) {
		super(output);
	}

	@Override
	public String getName() {
		return "clear";
	}

	@Override
	public int getMaximumParams() {
		
		return 0;
	}

	@Override
	public int getMinimumParams() {
		return 0;
	}

	@Override
	public String[] getAliases()
	{
		return new String[]{"c","clearthis","cleanup","wipe"};
		
	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		server.sendMessage(client,ServerMessenger.clear());
		
	}

	@Override
	protected void addPerms(ArrayList<String> perms) {
		perms.add("basic");
		perms.add("clear");
		
	}

}
