package com.flipturnapps.chatroom.command;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.kevinLibrary.net.ClientData;


public class UpCommandSetServerClient extends UpCommandTemplate {

	@Override
	public String getName() {
		return "setsc";
	}

	@Override
	public int getMaximumParams() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getMinimumParams() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	protected void upExecute(String[] params, ChatRoomServer server,
			ClientInfo client) {
		client.setServerClient(Boolean.parseBoolean(params[0]));

	}

}
