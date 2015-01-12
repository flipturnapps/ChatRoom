package com.flipturnapps.chatroom.command;

import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.command.CommandOutput;

public class UserCommandClearAll extends UserCommandTemplate {

	public UserCommandClearAll(CommandOutput output) {
		super(output);
	}

	@Override
	public String getName() {
		return "clearall";
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
		return new String[]{"cc","clearclients","cleanupall","wipeall"};
		
	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		server.sendAll(ServerMessenger.clear());
		
	}

	@Override
	protected void addPerms(ArrayList<String> perms) {
		perms.add("mod");
		perms.add("clearall");
		
	}

}
