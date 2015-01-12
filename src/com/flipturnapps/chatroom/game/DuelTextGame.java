package com.flipturnapps.chatroom.game;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.kevinLibrary.net.ClientData;

public abstract class DuelTextGame extends TextGame {

	private boolean bypassSubclassGetWinningClient = false;
	private ClientInfo nonForfeitingClient = null;
	protected DuelTextGame(String name, ChatRoomServer server, String password, boolean b) 
	{
		super(name, server, password, b);
	}

	@Override
	protected void playerGone(ClientData client) 
	{
		for(int i = 0; i < this.getPlayerCount(); i++)
		{
			if(this.getPlayerAt(i) != client)
				nonForfeitingClient = this.getPlayerAt(i);
		}
		bypassSubclassGetWinningClient = true;
	}

	@Override
	public int getMaxPlayers() 
	{
		return 2;
	}

	@Override
	public int getMinPlayers() 
	{
		return 2;
	}

	@Override
	protected boolean shouldStartIfPlayersMatch() 
	{
		return true;
	}

	protected boolean shouldAbuptlyStopGame()
	{
		return this.bypassSubclassGetWinningClient;
	}

	@Override
	protected final ClientInfo getWinningClient() 
	{
		if(this.bypassSubclassGetWinningClient == true)
			return this.nonForfeitingClient;
		else
			return winningClient();
	}

	protected abstract ClientInfo winningClient();
	public int getTargetPlayers()
	{
		return 2;
	}

}
