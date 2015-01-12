package com.flipturnapps.chatroom.command;

import java.io.IOException;
import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.command.CommandOutput;
import com.flipturnapps.kevinLibrary.net.ClientData;


public class UserCommandKick extends UserCommandTemplate {

	public UserCommandKick(CommandOutput output) {
		super(output);

	}

	@Override
	public String getName() {
		return "kick";
	}

	@Override
	public String getHelpText() {
		return "Kick a user.";
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
		return null;
	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		ClientInfo kick = server.getClientByName(params[0], server, client);
		server.kick(kick, client);
	}

	@Override
	protected void addPerms(ArrayList<String> perms) 
	{
		perms.add("kick");
		perms.add("mod");

	}

}
