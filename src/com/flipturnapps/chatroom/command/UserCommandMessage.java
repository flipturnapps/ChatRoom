package com.flipturnapps.chatroom.command;

import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.command.CommandOutput;
import com.flipturnapps.kevinLibrary.net.ClientData;


public class UserCommandMessage extends UserCommandTemplate {

	public UserCommandMessage(CommandOutput output) {
		super(output);
	}

	@Override
	public String getName() {
		return "message";
	}

	

	@Override
	public int getMaximumParams() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getMinimumParams() {
		return 2;
	}

	@Override
	public String[] getAliases() {
		return new String[]{"msg","t","tell","pm","persms","personalmessage","whisper"};
	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		ClientInfo recieve = server.getClientByName(params[0], server, client);
		if(recieve != null)
		{
			String text = "";
			for(int i = 1; i < params.length; i++)
			{
				text += params[i] + " ";
			}
			String recieveName = recieve.getName();
			String senderName = client.getName();
			server.sendMessage(client, ServerMessenger.getDisplayTextLine("[You --> " + recieveName + "] " + text,client.getColor()));
			server.sendMessage(recieve, ServerMessenger.getDisplayTextLine("[" + senderName + " --> You] " + text,client.getColor()));
			recieve.setReplyClient(client);
			client.setReplyClient(recieve);
			for(int i = 0; i < server.getClientCount(); i++)
			{
				ClientInfo testClient = server.getClient(i);
				if(testClient.getId() != client.getId() && testClient.getId() != recieve.getId() && testClient.canIntercept())
				{
					server.sendMessage(testClient, ServerMessenger.getDisplayTextLine("[" + senderName + " --> " + recieveName + "] " + text,client.getColor()));
				}
			}
		}

	}

	@Override
	protected void addPerms(ArrayList<String> perms) {
		perms.add("msg");
		perms.add("basic");

	}


}
