package com.flipturnapps.chatroom.game;

import com.flipturnapps.chatroom.net.ChatRoomClient;

public abstract class DownGameCommandTemplate extends GameCommandTemplate {

	@Override
	public Object execute(String commandName, String[] params, Object data) 
	{
		this.run(params, (ChatRoomClient) data);
		return null;
	}

	protected abstract void run(String[] params, ChatRoomClient client);
	
	@Override
	public boolean objectDataOK(Object data) 
	{
		return data instanceof ChatRoomClient;
	}

}
