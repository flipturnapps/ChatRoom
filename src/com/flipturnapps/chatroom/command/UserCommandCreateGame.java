package com.flipturnapps.chatroom.command;

import java.awt.Color;
import java.util.ArrayList;

import com.flipturnapps.chatroom.game.NimGame;
import com.flipturnapps.chatroom.game.RPSGame;
import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.command.CommandOutput;

public class UserCommandCreateGame extends UserCommandTemplate {

	public UserCommandCreateGame(CommandOutput output) {
		super(output);
	}

	@Override
	public String getName() 
	{
		return "newgame";
	}

	@Override
	public int getMaximumParams() 
	{
		return 4;
	}

	@Override
	public int getMinimumParams() {
		return 2;
	}

	@Override
	public String[] getAliases() {
		return new String[] {"game_new","gamenew"};
	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		String password;
		if(params.length >= 3)
			password = params[2];
		else
			password = "";
		int startThreshold = 3;
		try
		{
		if(params.length >= 4)
			startThreshold = Integer.parseInt(params[3]);
		}catch(Exception ex){};
		
		if(params[0].equals("RPS"))
			server.getGames().add(new RPSGame(params[1],server, password));
		else if(params[0].equals("nim"))
			server.getGames().add(new NimGame(params[1],server, password));
		else
			server.sendMessage(client, ServerMessenger.getDisplayTextLine("You cannot choose that game.", Color.black));

	}

	@Override
	protected void addPerms(ArrayList<String> perms) {
		perms.add("game");

	}

}
