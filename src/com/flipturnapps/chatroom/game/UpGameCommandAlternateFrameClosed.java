package com.flipturnapps.chatroom.game;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.kevinLibrary.net.ClientData;

public class UpGameCommandAlternateFrameClosed extends UpGameCommandTemplate
{	
	public static final String NAME = "altframeclosed";
	@Override
	public String getName() {
		return NAME;
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
	protected void run(String[] params, ChatRoomServer server, ClientInfo client) {
		for(int i = 0; i < server.getGames().size(); i++)
		{
			Game game = server.getGames().get(i);
			if(game.contains(client))
			{
				game.removePlayer(client);
			}
		}

	}

}
