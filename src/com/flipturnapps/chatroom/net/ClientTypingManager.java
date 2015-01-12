package com.flipturnapps.chatroom.net;

import java.awt.event.KeyEvent;


public class ClientTypingManager {

	private ChatRoomClient client;
	private long lastType;
	public ClientTypingManager (ChatRoomClient client)
	{
		this.client = client;
	}
	public void processType(KeyEvent e)
	{
		lastType = System.currentTimeMillis();
	}
	public long getTypeGap()
	{
		return System.currentTimeMillis() - lastType;
	}

}
