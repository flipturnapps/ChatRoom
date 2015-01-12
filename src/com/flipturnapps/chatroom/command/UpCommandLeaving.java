package com.flipturnapps.chatroom.command;

import java.io.IOException;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.chatroom.net.ServerMessenger;
import com.flipturnapps.kevinLibrary.net.ClientData;


public class UpCommandLeaving extends UpCommandTemplate {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "leaving";
	}

	@Override
	public int getMaximumParams() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinimumParams() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void upExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		server.sendAll(ServerMessenger.getDisplayTextLine(client.getName() + " has left.", 0,0,0));
		try {
			client.disconnect();
		} catch (IOException e) {
			System.out.println("IOE when disconnecting " + client.getName());
		}
		server.refreshClientList();
	}

}
