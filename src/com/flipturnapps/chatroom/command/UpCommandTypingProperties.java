package com.flipturnapps.chatroom.command;

import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.kevinLibrary.net.ClientData;

public class UpCommandTypingProperties extends UpCommandTemplate {

	public static final String NAME = "typingProperties";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getMaximumParams() {
		return 2;
	}

	@Override
	public int getMinimumParams() {
		return 2;
	}

	@Override
	protected void upExecute(String[] params, ChatRoomServer server, ClientInfo client) {
		client.setTypeGap(Long.parseLong(params[0]));
		client.setInputBoxNotEmpty(Boolean.parseBoolean(params[1]));

	}

}
