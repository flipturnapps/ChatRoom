package com.flipturnapps.chatroom.game;

import com.flipturnapps.chatroom.command.UpCommandWrapper;
import com.flipturnapps.chatroom.net.ChatRoomServer;
import com.flipturnapps.chatroom.net.ClientInfo;
import com.flipturnapps.kevinLibrary.net.ClientData;

public abstract class UpGameCommandTemplate extends GameCommandTemplate {

	@Override
	public Object execute(String commandName, String[] params, Object data) 
	{
		UpCommandWrapper wrapper = (UpCommandWrapper) data;
		this.run(params, wrapper.server, wrapper.client);
		return null;
	}

	protected abstract void run(String[] params, ChatRoomServer server, ClientInfo info);
	
	@Override
	public boolean objectDataOK(Object data) 
	{
		return data instanceof UpCommandWrapper;
	}

}
