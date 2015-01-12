package com.flipturnapps.chatroom.command;

import java.awt.Color;
import java.util.ArrayList;

import com.flipturnapps.chatroom.game.AlreadyInGameException;
import com.flipturnapps.chatroom.game.Game;
import com.flipturnapps.chatroom.game.GameAlreadyStartedException;
import com.flipturnapps.chatroom.game.GameFullException;
import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.command.CommandOutput;

public class UserCommandJoinGame extends UserCommandTemplate {

	public UserCommandJoinGame(CommandOutput output) {
		super(output);
	}

	@Override
	public String getName() {
		return "joingame";
	}

	@Override
	public int getMaximumParams() {
		return 2;
	}

	@Override
	public int getMinimumParams() {
		return 1;
	}

	@Override
	public String[] getAliases() {
		return new String[] {"join", "game_join"};
	}

	@Override
	protected void userExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		String password;
		if(params.length == 2)
			password = params[1];
		else
			password = "";
		for(int i = 0; i < server.getGames().size(); i++)
		{
			if(server.getGames().get(i).getCausalName().equalsIgnoreCase(params[0]))
			{
				try {
					Game game = server.getGames().get(i);
					if(game.getPassword().equals(password))
					{
					server.getGames().get(i).addPlayer(client);
					server.sendMessage(client, ServerMessenger.getDisplayTextLine("You joined " + params[0] + ".", Color.black));
					}
					else
						server.sendMessage(client, ServerMessenger.getDisplayTextLine("Bad password. Could not join.", Color.black));
					} catch (GameFullException e) {
					server.sendMessage(client, ServerMessenger.getDisplayTextLine("Game is full.", Color.black));
				} catch (GameAlreadyStartedException e) {
					server.sendMessage(client, ServerMessenger.getDisplayTextLine("Game has already begun.", Color.black));
				} catch (AlreadyInGameException e) {
					server.sendMessage(client, ServerMessenger.getDisplayTextLine("You are already in a game.", Color.black));
				}
			}
		}

	}

	@Override
	protected void addPerms(ArrayList<String> perms) {
		perms.add("game");

	}

}
