package com.flipturnapps.chatroom.command;

import java.awt.Color;
import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.command.CommandOutput;

public class UserCommandPrintName extends UserCommandTemplate {

	public UserCommandPrintName(CommandOutput output) {
		super(output);
	}

	@Override
	public String getName() {
		return "printname";
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
	public String[] getAliases() {
		return new String[] {"who","whoami","name","username","n","pn"};
	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server, ClientInfo client) {
		String name = client.getName();
		server.sendMessage(client, ServerMessenger.getDisplayTextLine("Your ChatRoom username is: \"" + name + "\"", Color.black));

	}

	@Override
	protected void addPerms(ArrayList<String> perms) {
		perms.add("basic");
		perms.add("printname");

	}

}
