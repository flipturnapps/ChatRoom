package com.flipturnapps.chatroom.net;
import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import com.flipturnapps.chatroom.game.GameInfo;
import com.flipturnapps.kevinLibrary.command.CommandSpeaker;
import com.flipturnapps.kevinLibrary.helper.KevinColor;
import com.flipturnapps.kevinLibrary.net.ClientData;


public class ClientInfo extends ClientData implements CommandSpeaker{
	
	/*
	 * ClientInfo:
	 *  -Store basic information about the client (name, id, color, permissions)
	 *  -Use class statics to decide on an appropriate id and color during construction
	 *  -Be stored in ClientData's data
	 */

	private static final int COLOR_MAX = 10;
	private String name;
	private Color color;
	private int id;
	private boolean isServerClient;
	private ArrayList<String> perms;
	private ClientInfo replyClient;
	private boolean canIntercept;
	private GameInfo gameInfo;
	private long typeGap;
	private boolean inputBoxNotEmpty;
	private ChatRoomServer server;
	//static variables
	private static int idBookmark;
	
	static
	{
		idBookmark = 0;
	}
	public ClientInfo(Socket socket, ChatRoomServer server) throws IOException
	{
		super(socket, server);
		perms = new ArrayList<String>();
		
		id = idBookmark;
		idBookmark++;
		int c = id % COLOR_MAX;
		switch(c)
		{
		case 0:
			color = KevinColor.kred;  break;
		case 1:
			color = KevinColor.kblue;  break;
		case 2:
			color = KevinColor.kgreen;  break;
		case 3:
			color = KevinColor.korange;  break;
		case 4:
			color = KevinColor.kpurple;  break;
		case 5:
			color = KevinColor.klime;  break;
		case 6:
			color = KevinColor.kdarkbrown;  break;
		case 7:
			color = KevinColor.kpink;  break;
		case 8:
			color = KevinColor.kcyan;  break;
		case 9:
			color = KevinColor.kblack;  break;
		}
		perms.add("basic");
		perms.add("msg");
		perms.add("game");
		this.server = server;
		this.setRegularClient(false);
		this.sendMessage(server.getRoomName());
	}
	public void sendDisplayString(String msg)
	{
		server.sendMessage(this, ServerMessenger.getDisplayTextLine(msg, Color.BLACK));
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		this.setRegularClient(true);
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public ArrayList<String> getPermsOwned() {
		
		return perms;
	}
	public void addPerm(String string) {
		perms.add(string);
		
	}
	public void remPerm(String string) {
		perms.remove(string);
		
	}
	public boolean isServerClient() {
		return isServerClient;
	}
	public void setServerClient(boolean isServerClient) {
		this.isServerClient = isServerClient;
		if(isServerClient)
		{
			perms.add("op");
		}
		else
		{
			perms.remove("op");
		}
	}
	public void setReplyClient(ClientInfo client) 
	{
		this.replyClient = client;
		
	}
	public boolean canIntercept() 
	{
		
		return canIntercept;
	}
	public ClientData getReplyClient() {
		return this.replyClient;
	}
	public String getReplyClientName() 
	{
		return (this.replyClient.getName());
	}
	public void setCanIntercept(boolean intercept) 
	{
		this.canIntercept = intercept;
		
	}
	public GameInfo getGameInfo() 
	{
		return gameInfo;
	}
	public void setGameInfo(GameInfo info) 
	{
		gameInfo = info;
	}
	public long getTypeGap() {
		return typeGap;
	}
	public void setTypeGap(long typeGap) {
		this.typeGap = typeGap;
	}
	public boolean isInputBoxNotEmpty() {
		return inputBoxNotEmpty;
	}
	public void setInputBoxNotEmpty(boolean inputBoxNotEmpty) {
		this.inputBoxNotEmpty = inputBoxNotEmpty;
	}
	



}
