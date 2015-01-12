package com.flipturnapps.chatroom.game;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;

public class FileTransferGame extends Game {

	public FileTransferGame(String name, ChatRoomServer server,
			String password) {
		super(name, server, password, false);
	}

	@Override
	public int getMaxPlayers()
	{
		return Integer.MAX_VALUE;
	}

	@Override
	public int getMinPlayers() {
		return 2;
	}

	@Override
	public String getOfficialName() {
		return "File-Transfer";
	}

	@Override
	protected GameInfo makeNewGameInfo() 
	{
		return new GameInfo();
	}

	@Override
	protected void playerLeft(ClientInfo client) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void playerAddedSuccessfully(ClientInfo data) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void gameJustStarted() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void doSetupSpotlight() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean shouldStartIfPlayersMatch() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void runGame() {
		// TODO Auto-generated method stub

	}

	@Override
	protected ClientInfo getWinningClient() {
		// TODO Auto-generated method stub
		return null;
	}

}
