package com.flipturnapps.chatroom.game;

import java.util.ArrayList;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.helper.ThreadHelper;

public abstract class Game 
{
	public static final int GAME_PHASE_SETUP = 0;
	public static final int GAME_PHASE_RUNNING = 1;
	public static final int GAME_PHASE_FINISHED = 2;

	private String causalName;
	private ArrayList<ClientInfo> players;
	private int gamePhase = GAME_PHASE_SETUP;
	private ChatRoomServer server;
	private boolean canOutput;
	private String password;

	public abstract int getMaxPlayers();
	public abstract int getMinPlayers();
	public abstract String getOfficialName();

	protected abstract GameInfo makeNewGameInfo();
	protected abstract void playerLeft(ClientInfo client);
	protected abstract void playerAddedSuccessfully(ClientInfo data);
	protected abstract void gameJustStarted();
	protected abstract void doSetupSpotlight();
	protected abstract boolean shouldStartIfPlayersMatch();
	protected abstract void runGame();
	protected abstract ClientInfo getWinningClient();

	public Game(String name, ChatRoomServer server, String password)
	{
		this(name,server,password,true);
	}
	protected Game(String name, ChatRoomServer server, String password, boolean output)
	{
		this.canOutput = output;
		this.server = server;
		players = new ArrayList<ClientInfo>();
		this.setGamePhase(GAME_PHASE_SETUP);
		this.setCausalName(name);
		this.setPassword(password);
		if(canOutput)
			this.server.sendAllServerMessage("The " + this.getCausalName() + " game of " +  this.getOfficialName() + " is aquiring players.");
		new Thread(new GameManager()).start();
	}	
	protected ChatRoomServer getServer()
	{
		return server;
	}
	protected void gameEnded()
	{
		if(this.canOutput)
		{
			String winName = this.getWinningClient().getName();
			server.sendAllServerMessage(winName + " won the game \"" + this.getCausalName() + "\" of " + this.getOfficialName() + "!");
			this.getServer().getGames().remove(this);
			for(int i = 0; i < this.getPlayerCount(); i++)
			{
				this.getPlayerAt(i).setGameInfo(null);
			}
		}
	}
	public void addPlayer(ClientInfo data) throws GameFullException, GameAlreadyStartedException, AlreadyInGameException
	{
		this.throwAddPlayerExceptions(data);
		this.addClientToPlayerlist(data);
	}
	public void addPlayerUnsafe(ClientInfo data)
	{
		this.addClientToPlayerlist(data);
	}
	private void addClientToPlayerlist(ClientInfo data)
	{
		players.add(data);
		if(this.canOutput)
		{
			data.setGameInfo(this.makeNewGameInfo());
			playerAddedSuccessfully(data);
		}
	}
	private void throwAddPlayerExceptions(ClientInfo data) throws GameFullException, GameAlreadyStartedException, AlreadyInGameException
	{
		if(players.size() == this.getMaxPlayers())
		{
			throw new GameFullException();
		}
		if(this.getGamePhase() != Game.GAME_PHASE_SETUP)
		{
			throw new GameAlreadyStartedException();
		}
		if(data.getGameInfo() != null)
		{
			throw new AlreadyInGameException();
		}
	}
	public int getPlayerCount()
	{
		return players.size();
	}
	public ClientInfo getPlayerAt(int i)
	{
		return players.get(i);
	}
	public GameInfo getGameInfoAt(int i)
	{
		return (players.get(i)).getGameInfo();
	}
	protected void removeDisconnectedPlayers()
	{
		for(int i = 0; i < this.getPlayerCount(); i++)
		{
			ClientInfo client = this.getPlayerAt(i);
			boolean contains = false;
			for(int x = 0; x < server.getClientCount(); x++)
			{
				if(server.getClient(x).getId() == client.getId())
					contains = true;
			}
			if(!contains)
			{
				removePlayer(client);
			}
		}

	}
	public void removePlayer(ClientInfo client)
	{
		this.playerLeft(client);
		this.players.remove(client);
		client.setGameInfo(null);
		this.getServer().sendMessage(client, ServerMessenger.sendGameModify(TextGameCloseWindowDownCommand.NAME));
	}
	public String getCausalName() {
		return causalName;
	}
	public void setCausalName(String causalName) {
		this.causalName = causalName;
	}
	public int getGamePhase() {
		return gamePhase;
	}
	public void setGamePhase(int gamePhase) {
		this.gamePhase = gamePhase;
	}
	private class GameManager implements Runnable
	{
		public void run() 
		{		
			while(getGamePhase() == Game.GAME_PHASE_SETUP)
			{
				doSetupSpotlight();
				removeDisconnectedPlayers();
				if(players.size() >= getMinPlayers() && players.size() <= getMaxPlayers() && shouldStartIfPlayersMatch())
				{
					runChildGameRunner();
				}
				ThreadHelper.sleep(100);
			}
			while(getGamePhase() != Game.GAME_PHASE_FINISHED)
			{
				removeDisconnectedPlayers();
				ThreadHelper.sleep(500);
			}
			gameEnded();
		}
	}
	private void runChildGameRunner()
	{
		new Thread(new ChildGameRunner()).start();
	}
	private class ChildGameRunner implements Runnable
	{
		public void run() 
		{
			setGamePhase(Game.GAME_PHASE_RUNNING);
			if(canOutput)
				getServer().sendAllServerMessage("The " + getCausalName() + " game of " +  getOfficialName() + " has started.");
			runGame();
			setGamePhase(Game.GAME_PHASE_FINISHED);
		}
	}
	public boolean contains(ClientInfo data)
	{
		if(players.contains(data))
			return true;
		return false;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	protected boolean getOutputAllowed() {
		return this.canOutput;
	}




}
