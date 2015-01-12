package com.flipturnapps.chatroom.command;

import java.awt.Color;
import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.command.CommandOutput;
import com.flipturnapps.kevinLibrary.net.ClientData;

public class UserCommandDisplayTypingProperties extends UserCommandTemplate {

	public UserCommandDisplayTypingProperties(CommandOutput output) {
		super(output);
	}

	@Override
	public String getName() {
		return "displaytypingproperties";
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
		return new String[]{"dtp"};
	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		ClientInfo getClient = server.getClientByName(params[0], server, client);
		if(getClient != null)
		{
			String output = "" + params[0] + " hit a key " + getClient.getTypeGap() + " ms ago. (boxnotempty = " + getClient.isInputBoxNotEmpty() + ")";
			server.sendMessage(client, ServerMessenger.getDisplayTextLine(output, Color.black));
		}
	}

	@Override
	protected void addPerms(ArrayList<String> perms) {
		perms.add("basic");
		perms.add("displaytypingproperties");

	}

}
