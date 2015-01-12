package com.flipturnapps.chatroom.command;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;


public class UpCommandWrapper {

	public UpCommandWrapper(ChatRoomServer s, ClientInfo c)
	{
		server = s;
		client = c;
	}
	public ChatRoomServer server;
	public ClientInfo client;

}
