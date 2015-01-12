package com.flipturnapps.chatroom.game;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.Start;
import com.flipturnapps.kevinLibrary.net.ClientData;

public class TextGameSendStringUpCommand extends UpGameCommandTemplate
{
	public static final String name = "textgamesend";
	
	@Override
	public String getName() 
	{
		return name;
	}

	@Override
	public int getMaximumParams() 
	{
		return 1;
	}

	@Override
	public int getMinimumParams() 
	{
		return 1;
	}

	@Override
	protected void run(String[] params, ChatRoomServer server, ClientInfo client) 
	{
		if(!(client.getGameInfo() instanceof TextGameInfo))
		{
			Start.forceExit("GameUpCommand send tried to parse clientInfo.getGameInfo() as TextGameInfo but failed.");
		}
		((TextGameInfo) (client.getGameInfo())).getMessagesFrom().add(params[0]);
	}
	
}
