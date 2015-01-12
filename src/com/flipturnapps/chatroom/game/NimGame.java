package com.flipturnapps.chatroom.game;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.Start;
import com.flipturnapps.kevinLibrary.helper.Numbers;
import com.flipturnapps.kevinLibrary.helper.ThreadHelper;

public class NimGame extends DuelTextGame {

	private ClientInfo winningClient;
	private static final String OFFICIAL_NAME = "Nim Duel";
	public NimGame(String name, ChatRoomServer server, String password) 
	{
		this(name, server, password, true);
	}


	protected NimGame(String name, ChatRoomServer server, String password, boolean b) {
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
			Start.forceExit("Nim runGame() failed because game.getPlayerAt(1) is out of bounds but it shouldnt be.");
		}		
		TextGameInfo info1 = (TextGameInfo) client1.getGameInfo();
		TextGameInfo info2 = (TextGameInfo) client2.getGameInfo();
		int stoneCount = Numbers.random(500, 5000);
		int turnCount = Numbers.random(0, 1);
		while(!this.shouldAbuptlyStopGame() && stoneCount > 0)
		{
			ClientInfo needClient;
			TextGameInfo needInfo;
			if(turnCount % 2 == 0)
			{
				needClient = client1;
				needInfo = info1;
			}
			else
			{
				needClient = client2;
				needInfo = info2;
			}
			int response = -1;
			needInfo.getMessagesFrom().clear();
			this.sendTo(needClient, "There are " + stoneCount + " stones in the stack. How many will you take?");
			while(!this.shouldAbuptlyStopGame() && (response == -1))
			{
				if(needInfo.getMessagesFrom().size() > 0)
				{
					try
					{
						response = Integer.parseInt(needInfo.getMessagesFrom().get(needInfo.getMessagesFrom().size() - 1));
					}
					catch(RuntimeException ex)
					{
					}
					if(response <= 0)
						response = -1;
					double doubleCount = stoneCount + 0.0;
					double doubleHalf = doubleCount / 2;
					int maxValAllowed = (int) doubleHalf;
					if(maxValAllowed != doubleHalf)
						maxValAllowed++;					
					if(response > maxValAllowed)
						response = -1;
				}
				ThreadHelper.sleep(100);

			}
			needInfo.getMessagesFrom().clear();
			if(this.shouldAbuptlyStopGame())
				break;
			stoneCount -= response;
			this.sendAll(needClient.getName() + " took " + response + " stones from the stack.  The stack went from " + (stoneCount + response) + " to " + stoneCount + " stones.");
			turnCount++;
		}
		if(turnCount % 2 == 0)
		{
			this.winningClient = client1;
		}
		else
			this.winningClient = client2;
		
	}


}
