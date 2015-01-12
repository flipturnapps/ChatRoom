package com.flipturnapps.chatroom.game;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.net.ClientData;

public abstract class TextGame extends Game 
{
	private long lastSentCurrentPlayers = 0;
	protected TextGame(String name, ChatRoomServer server, String password, boolean b) 
	{
		super(name, server, password, b);
	}
	protected void doSetupSpotlight()
	{
		if(System.currentTimeMillis() - this.lastSentCurrentPlayers > 5000 && this.getOutputAllowed())
		{
			String currentPlayers = "Current Players: ";
			for(int i = 0; i < this.getPlayerCount(); i++)
			{
				ClientInfo client = this.getPlayerAt(i);
				String name = client.getName();
				currentPlayers += name + ",";
			}
			currentPlayers = currentPlayers.substring(0, currentPlayers.length() -1);
			this.sendAll(currentPlayers);
			this.lastSentCurrentPlayers = System.currentTimeMillis();
		}
	}
	public abstract int getTargetPlayers();
	protected final GameInfo makeNewGameInfo()
	{
		return this.getNewGameInfo();

	}
	protected abstract TextGameInfo getNewGameInfo();

	@Override
	protected void playerLeft(ClientInfo client) 
	{
		this.sendAll(client.getName() + " has left the game." );
		this.playerGone(client);
	}
	protected final void sendAll(String string)
	{
		for(int i = 0; i < this.getPlayerCount(); i++)
		{
			this.sendTo(this.getPlayerAt(i),string);
		}
	}
	protected final void sendTo(ClientInfo client, String string)
	{
		this.getServer().sendMessage(client, ServerMessenger.sendGameModify(TextGameDisplayMessageDownCommand.name + "`" + string));
	}
	protected abstract void playerGone(ClientData client);

	@Override
	protected void playerAddedSuccessfully(ClientInfo client) 
	{
		this.sendAll(client.getName() + " has joined the game.");
		((TextGameInfo) client.getGameInfo()).getMessagesFrom().clear();
	}

	@Override
	protected void gameJustStarted() 
	{
		if(this.getOutputAllowed())
			this.sendAll("The game will now begin.");
	}
	protected void gameEnded()
	{
		super.gameEnded();
		if(this.getOutputAllowed())
		{
			for(int i = 0; i < this.getPlayerCount(); i++)
			{
				ClientInfo client = this.getPlayerAt(i);
				this.getServer().sendMessage(client, ServerMessenger.sendGameModify(TextGameCloseWindowDownCommand.NAME));
			}
		}
	}

}
