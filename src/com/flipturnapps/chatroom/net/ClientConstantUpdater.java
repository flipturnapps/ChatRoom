package com.flipturnapps.chatroom.net;

import com.flipturnapps.kevinLibrary.helper.ThreadHelper;

public class ClientConstantUpdater implements Runnable {

	private ChatRoomServer server;
	
	public ClientConstantUpdater(ChatRoomServer server) 
	{
		this.server = server;
		
	}

	@Override
	public void run() 
	{
		ThreadHelper.sleep(1000);
		while(true)
		{
			server.sendClientTypingInfo();
			ThreadHelper.sleep(server.getAutoUpdateWaitTime());
		}

	}

}
