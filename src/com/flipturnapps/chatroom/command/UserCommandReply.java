package com.flipturnapps.chatroom.command;

import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ClientMessenger;
import com.flipturnapps.kevinLibrary.command.CommandOutput;
import com.flipturnapps.kevinLibrary.net.ClientData;


public class UserCommandReply extends UserCommandTemplate {

	public UserCommandReply(CommandOutput output) {
		super(output);
	}

	@Override
	public String getName() {
		return "reply";
	}

	@Override
	public int getMaximumParams() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getMinimumParams() {
		return 1;
	}

	@Override
	public String[] getAliases() {
		return new String[] {"r","re","re-message","back"};
	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		String text = "";
		for(int i = 0; i < params.length; i++)
		{
			text += params[i] + " ";
		}
		server.newMessage(ClientMessenger.parseCommand("/message " + client.getReplyClientName() +  " " + text),client); 

	}

	@Override
	protected void addPerms(ArrayList<String> perms) {
		perms.add("msg");
		perms.add("basic");

	}

}
