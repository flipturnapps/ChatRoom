package com.flipturnapps.chatroom.command;

import java.awt.Color;
import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.command.CommandOutput;
import com.flipturnapps.kevinLibrary.net.ClientData;


public class UserCommandSetColor extends UserCommandTemplate {

	public UserCommandSetColor(CommandOutput output) {
		super(output);
	}

	@Override
	public String getName() {
		return "setcolor";
	}

	@Override
	public String getHelpText() {
		return "setcolor <user> <red> <green> <blue>";
	}

	@Override
	public int getMaximumParams() {
		return 4;
	}

	@Override
	public int getMinimumParams() {
		return 3;
	}

	@Override
	public String[] getAliases() {
		return new String[]{"color"};
	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		int paramNum = params.length;
		ClientInfo color;
		if(paramNum == 4)
			color = server.getClientByName(params[0], server, client);
		else
			color = client;
		if(color != null)
		{
			try
			{
			int red = Integer.parseInt(params[paramNum - 3]);
			int green =  Integer.parseInt(params[paramNum - 2]);
			int blue =  Integer.parseInt(params[paramNum - 1]);
			color.setColor(new Color(red,green,blue));
			server.sendAll(ServerMessenger.getDisplayTextLine(client.getName() + " has a new color.", 0,0,0)); 
			}
			catch(Exception ex)
			{
				server.sendMessage(client, ServerMessenger.getDisplayTextLine("Please make sure your rgb values are integers.",0,0,0));
			}
		}

	}

	@Override
	protected void addPerms(ArrayList<String> perms) {
		perms.add("mod");
		perms.add("setcolor");

	}

}
