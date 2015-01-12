package com.flipturnapps.chatroom.game;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.Start;
import com.flipturnapps.kevinLibrary.net.ClientData;

public class RPSGame extends DuelTextGame {

	private ClientInfo winningClient;
	private static final String OFFICIAL_NAME = "Rock Paper Scissors Duel";
	public RPSGame(String name, ChatRoomServer server, String password) 
	{
		this(name, server, password, true);
	}
	

	protected RPSGame(String name, ChatRoomServer server, String password, boolean b) {
		super(name,server,password,b);
	}


	@Override
	protected ClientInfo winningClient() 
	{
		return winningClient;
	}

	@Override
	protected TextGameInfo getNewGameInfo() 
	{
		return new TextGameInfo();
	}

	@Override
	public String getOfficialName() 
	{
		return OFFICIAL_NAME;
	}

	@Override
	protected void runGame() 
	{
		this.sendAll("The game is starting.");
		ClientInfo client1 = null;
		ClientInfo client2 = null;
		try
		{
			client1 = this.getPlayerAt(0);
			client2 = this.getPlayerAt(1);
		}
		catch(Exception ex)
		{
			Start.forceExit("RPS runGame() failed because game.getPlayerAt(1) is out of bounds but it shouldnt be.");
		}
		while(!this.shouldAbuptlyStopGame())
		{			
			int response1 = -1;
			int response2 = -1;
			TextGameInfo info1 = (TextGameInfo) client1.getGameInfo();
			TextGameInfo info2 = (TextGameInfo) client2.getGameInfo();
			this.sendAll("Please choose rock, paper, or scissors.");
			boolean sentGot1RepsonseMessage = false;
			boolean sentGot2RepsonseMessage = false;
			while(!this.shouldAbuptlyStopGame() && (response1 == -1 || response2 == -1))
			{
				if(info1.getMessagesFrom().size() != 0 && response1 == -1)
				{
					String last = info1.getMessagesFrom().get(info1.getMessagesFrom().size()-1);
					if(last.equalsIgnoreCase("rock"))
						response1 = 0;
					if(last.equalsIgnoreCase("paper"))
						response1 = 1;
					if(last.equalsIgnoreCase("scissors"))
						response1 = 2;
				}
				if(info2.getMessagesFrom().size() != 0 && response2 == -1)
				{
					String last = info2.getMessagesFrom().get(info2.getMessagesFrom().size()-1);
					if(last.equalsIgnoreCase("rock"))
						response2 = 0;
					if(last.equalsIgnoreCase("paper"))
						response2 = 1;
					if(last.equalsIgnoreCase("scissors"))
						response2 = 2;
				}
				if(response1 != -1 && !sentGot1RepsonseMessage )
				{
					this.sendAll(client1.getName() + " has submitted a response.");
					sentGot1RepsonseMessage = true;
				}
				if(response2 != -1 && !sentGot2RepsonseMessage )
				{
					this.sendAll(client2.getName() + " has submitted a response.");
					sentGot2RepsonseMessage = true;
				}

			}
			if(this.shouldAbuptlyStopGame())
				break;
			if(response1 == response2)
			{
				info1.getMessagesFrom().clear();
				info2.getMessagesFrom().clear();
				this.sendAll("The players tied. A rematch will now begin.");
				continue;
			}
			if(response1 - response2 == 1)
				this.winningClient = client1;
			if(response2 - response1 == 1)
				this.winningClient = client2;
			if(response1 - response2 == 2)
				this.winningClient = client2;
			if(response2 - response1 == 2)
				this.winningClient = client1;
			this.sendAll(winningClient.getName() + " won the game.");
			break;
		}

	}

}
